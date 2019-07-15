package taxipark

fun TaxiPark.findFakesDrivers1(): Set<Driver> =
        allDrivers.filter { d -> trips.none { it.driver == d } }.toSet()

fun TaxiPark.findFakesDrivers2(): Set<Driver> =
        // allDrivers.minus(trips.map { it.driver })
        allDrivers - trips.map { it.driver } // equals minus or (-)

fun TaxiPark.findFaithfulPassengers1(minTrips: Int): Set<Passenger> =
        trips
                .flatMap(Trip::passengers)
                .groupBy { passenger -> passenger }
                .filterValues { group -> group.size >= minTrips }
                .keys

fun TaxiPark.findFaithfulPassengers2(minTrips: Int): Set<Passenger> =
        allPassengers
                .filter { p ->
                    trips.count { p in it.passengers } >= minTrips
                }
                .toSet()

fun TaxiPark.findFrequentPassengers1(driver: Driver): Set<Passenger> =
        trips
                .filter { trip -> trip.driver == driver }
                .flatMap(Trip::passengers)
                .groupBy { passenger -> passenger }
                .filterValues { group -> group.size > 1 }
                .keys

fun TaxiPark.findFrequentPassengers2(driver: Driver): Set<Passenger> =
        allPassengers
                .filter { p ->
                    trips.count { it.driver == driver && p in it.passengers } > 1
                }.toSet()

fun TaxiPark.findSmartPassengers1(): Set<Passenger> {
    val (tripsWithDiscount, tripsWithoutDiscount) = trips.partition { it.discount != null }

    return allPassengers
            .filter { passenger ->
                tripsWithDiscount.count { passenger in it.passengers } >
                        tripsWithoutDiscount.count { passenger in it.passengers }
            }
            .toSet()
}

fun TaxiPark.findSmartPassengers2(): Set<Passenger> =
        allPassengers
                .associate { p -> p to trips.filter { t -> p in t.passengers } }
                .filterValues { group ->
                    val (withDiscount, withoutDiscount) = group
                            .partition { it.discount != null }
                    withDiscount.size > withoutDiscount.size
                }
                .keys

fun TaxiPark.findSmartPassengers3(): Set<Passenger> =
        allPassengers.filter { p ->
            val withDiscount = trips.count { t -> p in t.passengers && t.discount != null }
            val withoutDiscount = trips.count { t -> p in t.passengers && t.discount == null }

            withDiscount > withoutDiscount
        }.toSet()

fun TaxiPark.findTheMostFrequentTriDurationPeriod(): IntRange? {
    return trips
            .groupBy {
                val start = it.duration / 10 * 10
                val end = start + 9
                start..end
            }
            .toList()
            .maxBy { (_, group) -> group.size }
            ?.first
}

fun TaxiPark.checkParetoPrinciple1(): Boolean {
    if (trips.isEmpty()) return false

    // Total de ingresos
    val totalIncome = trips.sumByDouble(Trip::cost)

    val sortedDriversIncome: List<Double> = trips
            .groupBy(Trip::driver)
            .map { (_, tripsByDriver) -> tripsByDriver.sumByDouble(Trip::cost) }
            .sortedDescending()

    val numberOfTopDrivers = (0.2 * allDrivers.size).toInt()
    val incomeByTopDrivers = sortedDriversIncome
            .take(numberOfTopDrivers)
            .sum()

    return incomeByTopDrivers >= 0.8 * totalIncome
}
