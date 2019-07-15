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