package com.microdesarrollo.alex_aguero_20240629

object FlightRepository {

    private val flights = mutableListOf(
        Flight(1, "STGO AMB", "Puerto Montt", "2024-07-01", "10:00"),
        Flight(2, "STGO AMB", "Iquique", "2024-07-02", "12:00"),
        Flight(3, "STGO AMB", "La Serena", "2024-07-03", "14:00"),
        Flight(4, "STGO AMB", "Antofagasta", "2024-07-04", "16:00"),
        Flight(5, "STGO AMB", "Concepción", "2024-07-05", "18:00")
    )

    private val reservations = mutableListOf<Reservation>()

    fun getFlights() = flights

    fun getReservations() = reservations

    fun addReservation(flight: Flight) {
        reservations.add(Reservation(reservations.size + 1, flight))
    }

    fun removeReservation(reservation: Reservation) {
        reservations.remove(reservation)
    }
}
