package com.microdesarrollo.alex_aguero_20240629

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationHelper {

    private const val CHANNEL_ID = "flight_notifications"

    fun createNotificationChannel(context: Context) {
        val name = "Flight Notifications"
        val descriptionText = "Notifications for flight offers and reservations"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun showOfferNotification(context: Context) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_flight)
            .setContentTitle("New Flight Offers")
            .setContentText("Check out the latest flight offers!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    }

    fun showReservationNotification(context: Context, reservations: List<Reservation>) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_reservation)
            .setContentTitle("Upcoming Reservations")
            .setContentText("You have ${reservations.size} upcoming reservations.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(2, builder.build())
        }
    }
}
