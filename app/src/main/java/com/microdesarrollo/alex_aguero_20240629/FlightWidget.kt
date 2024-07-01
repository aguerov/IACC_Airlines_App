package com.microdesarrollo.alex_aguero_20240629

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class FlightWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE == intent.action) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)
            if (appWidgetIds != null) {
                for (appWidgetId in appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId)
                }
            }
        }
    }

    companion object {
        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.flight_widget)
            val reservations = FlightRepository.getReservations()

            views.setTextViewText(R.id.widgetText, if (reservations.isEmpty()) {
                context.getString(R.string.no_reservations)
            } else {
                reservations.joinToString(separator = "\n\n") {
                    "${it.flight.destination}\nFecha: ${it.flight.date}\nHora: ${it.flight.time}"
                }
            })

            // Configurar el intent para abrir ReservationActivity al hacer clic en el widget
            val intent = Intent(context, ReservationActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(R.id.widgetText, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
