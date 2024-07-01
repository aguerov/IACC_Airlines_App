package com.microdesarrollo.alex_aguero_20240629

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class NotificationService : Service() {

    private val handler = Handler()
    private val interval: Long = 120000 // 120 segundos

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        handler.postDelayed(runnable, interval)
        Log.d("NotificationService", "Service created")
    }

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            sendNotification()
            handler.postDelayed(this, interval)
        }
    }

    private fun sendNotification() {
        val flights = FlightRepository.getFlights()
        if (flights.isNotEmpty()) {
            val randomFlight = flights.random()
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_flight)
                .setContentTitle("Nueva Promoción de Vuelo")
                .setContentText("Vuelo a ${randomFlight.destination} el ${randomFlight.date} a las ${randomFlight.time}")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, notification)
            Log.d("NotificationService", "Notification sent")
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Promociones de Vuelos"
            val descriptionText = "Canal para las notificaciones de promociones de vuelos"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("NotificationService", "Service started")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        Log.d("NotificationService", "Service destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        const val CHANNEL_ID = "flight_promotions_channel"
        const val NOTIFICATION_ID = 1
    }
}