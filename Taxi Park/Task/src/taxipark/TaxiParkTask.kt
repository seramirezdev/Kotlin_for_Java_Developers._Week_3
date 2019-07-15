package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> {

    val driversWithTrips = mutableSetOf<String>()

    trips.forEach { driversWithTrips.add(it.driver.name) }
    val drivers = allDrivers.filter { it.name !in driversWithTrips }

    return drivers.toSet()
}

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
    val passengersWithTrips = mutableListOf<Passenger>()

    trips.forEach { it.passengers.forEach { passengersWithTrips.add(it) } }

    val passengers = passengersWithTrips.groupBy { it }.filter { (_, count) -> count.size >= minTrips }

    return passengers.keys
}

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
    val t = mutableSetOf<Passenger>()
    trips.filter { it.driver == driver }.forEach {
        it.passengers.forEach { t.add(it) }
    }

    return t
}


/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        TODO()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    return TODO()
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    TODO()
}

fun main() {


    val drivers = mutableSetOf(Driver("Sergio"), Driver("Andrés"), Driver("José"), Driver("Julio"))
    val passanger = mutableSetOf(Passenger("S"), Passenger("A"), Passenger("J"), Passenger("O"), Passenger("P"))

    val trips = mutableListOf<Trip>()

    val trip1 = Trip(drivers.elementAt(0), setOf(passanger.elementAt(0), passanger.elementAt(1)), duration = 30, distance = 10.0, discount = 0.2)
    val trip2 = Trip(drivers.elementAt(0), setOf(passanger.elementAt(0), passanger.elementAt(1), passanger.elementAt(2)), duration = 30, distance = 10.0, discount = 0.2)
    val trip3 = Trip(drivers.elementAt(1), setOf(passanger.elementAt(0)), duration = 30, distance = 10.0, discount = 0.2)

    trips.add(trip1)
    trips.add(trip2)
    trips.add(trip3)

    val taxi = TaxiPark(drivers, passanger, trips)

    //println(taxi.findFakeDrivers())
    // println(taxi.findFaithfulPassengers(3))
    // println(taxi.findFrequentPassengers(drivers.elementAt(0)))
}