package com.example.foodtracker

// Import necessary packages and classes
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build

// NotificationUtils object definition
object NotificationUtils {

    // Function to schedule a notification
    fun scheduleNotification(context: Context, notificationData: NotificationData) {
        // Get AlarmManager instance
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create an intent for the notification receiver
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            // Pass notification data as extras
            putExtra("notificationId", notificationData.id)
            putExtra("notificationTitle", notificationData.title)
            putExtra("notificationContent", notificationData.content)
        }

        // Create a PendingIntent for the notification
        val pendingIntent: PendingIntent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getBroadcast(context, notificationData.id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        } else {
            pendingIntent = PendingIntent.getBroadcast(context, notificationData.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        // Schedule the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, notificationData.dateTime, pendingIntent)
            }
        }
    }

    // Function to cancel a scheduled notification
    fun cancelNotification(context: Context, notificationId: Int) {
        // Get AlarmManager instance
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // Create an intent for the notification receiver
        val intent = Intent(context, NotificationReceiver::class.java)
        // Create a PendingIntent for the notification
        val pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        // Cancel the notification
        alarmManager.cancel(pendingIntent)
    }
}
