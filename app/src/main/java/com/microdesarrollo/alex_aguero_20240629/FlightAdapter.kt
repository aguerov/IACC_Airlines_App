package com.microdesarrollo.alex_aguero_20240629

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FlightAdapter(
    private val flights: List<Flight>,
    private val onFlightSelected: (Flight) -> Unit
) : RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flight_item, parent, false)
        return FlightViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = flights[position]
        holder.bind(flight, position == selectedPosition)
    }

    override fun getItemCount() = flights.size

    inner class FlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val flightDestination: TextView = itemView.findViewById(R.id.flightDestination)
        private val flightDateTime: TextView = itemView.findViewById(R.id.flightDateTime)

        fun bind(flight: Flight, isSelected: Boolean) {
            flightDestination.text = flight.destination
            flightDateTime.text = "${flight.date} - ${flight.time}"
            itemView.setBackgroundColor(if (isSelected) itemView.context.getColor(R.color.selectedItem) else itemView.context.getColor(android.R.color.transparent))
            itemView.setOnClickListener {
                if (selectedPosition != adapterPosition) {
                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                    notifyItemChanged(selectedPosition)
                    onFlightSelected(flight)
                }
            }
        }
    }
}
