package com.example.foodtracker

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

object NotificationUtils {

    @SuppressLint("UnspecifiedImmutableFlag", "ScheduleExactAlarm")
    fun scheduleNotification(context: Context, notificationData: NotificationData) {
        // Create an intent for the NotificationReceiver
        val intent = Intent(context, NotificationReceiver::class.java)

        // Add title and message as extras to the intent
        intent.putExtra("notificationTitle", notificationData.title)
        intent.putExtra("notificationContent", notificationData.content)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(
                context,
                notificationData.id.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getBroadcast(
                context,
                notificationData.id.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        // Get the AlarmManager service
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // schedule the notification
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            notificationData.dateTime,
            pendingIntent
        )
    }

    fun cancelNotification(context: Context, notificationId: Long) { // Change parameter type to Long
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(context, notificationId.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE) // Convert Long to Int
        } else {
            PendingIntent.getBroadcast(context, notificationId.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT) // Convert Long to Int
        }
        alarmManager.cancel(pendingIntent)
    }
}