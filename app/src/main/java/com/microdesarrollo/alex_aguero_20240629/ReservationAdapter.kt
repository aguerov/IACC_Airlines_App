package com.microdesarrollo.alex_aguero_20240629

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReservationAdapter(
    private val reservations: List<Reservation>,
    private val onReservationDeleted: (Reservation) -> Unit
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reservation_item, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.bind(reservation)
    }

    override fun getItemCount() = reservations.size

    inner class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val reservationFlight: TextView = itemView.findViewById(R.id.reservationFlight)
        private val deleteReservation: ImageView = itemView.findViewById(R.id.deleteReservation)

        fun bind(reservation: Reservation) {
            reservationFlight.text = reservation.flight.destination
            deleteReservation.setOnClickListener { onReservationDeleted(reservation) }
        }
    }
}
