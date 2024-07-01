package com.microdesarrollo.alex_aguero_20240629

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ReservationActivity : AppCompatActivity() {

    private lateinit var reservationRecyclerView: RecyclerView
    private lateinit var reservationAdapter: ReservationAdapter
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        reservationRecyclerView = findViewById(R.id.reservationRecyclerView)
        backButton = findViewById(R.id.backButton)

        reservationRecyclerView.layoutManager = LinearLayoutManager(this)
        reservationAdapter = ReservationAdapter(FlightRepository.getReservations(), this::onReservationDeleted)
        reservationRecyclerView.adapter = reservationAdapter

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun onReservationDeleted(reservation: Reservation) {
        FlightRepository.removeReservation(reservation)
        reservationAdapter.notifyDataSetChanged()
    }
}