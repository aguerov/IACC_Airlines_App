package com.microdesarrollo.alex_aguero_20240629

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var flightRecyclerView: RecyclerView
    private lateinit var flightAdapter: FlightAdapter
    private lateinit var reservationButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flightRecyclerView = findViewById(R.id.flightRecyclerView)
        reservationButton = findViewById(R.id.reservationButton)

        flightRecyclerView.layoutManager = LinearLayoutManager(this)
        flightAdapter = FlightAdapter(FlightRepository.getFlights(), this::onFlightSelected)
        flightRecyclerView.adapter = flightAdapter

        reservationButton.setOnClickListener {
            val intent = Intent(this, ReservationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onFlightSelected(flight: Flight) {
        FlightRepository.addReservation(flight)
        updateWidget()
    }

    private fun updateWidget() {
        val intent = Intent(this, FlightWidget::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            val ids = AppWidgetManager.getInstance(application).getAppWidgetIds(ComponentName(application, FlightWidget::class.java))
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        }
        sendBroadcast(intent)
    }
}
