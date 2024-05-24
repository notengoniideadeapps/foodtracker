package com.example.foodtracker

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews


class NotificationWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.notification_widget_provider)

            val intent = Intent(context, NotificationWidgetService::class.java)
            views.setRemoteAdapter(R.id.appwidget_list,                 intent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}