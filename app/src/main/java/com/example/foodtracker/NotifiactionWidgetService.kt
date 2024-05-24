package com.example.foodtracker

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService


class NotificationWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return NotificationRemoteViewsFactory(this.applicationContext)
    }
}

class NotificationRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private var notifications: List<NotificationData> = emptyList()

    override fun onCreate() {}

    override fun onDataSetChanged() {
        notifications = NotificationStorage.getNotifications(context)
    }

    override fun onDestroy() {}

    override fun getCount(): Int {
        return notifications.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val views = RemoteViews(context.packageName, R.layout.notification_item)
        views.setTextViewText(R.id.notification_text, notifications[position].title)
        return views
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}