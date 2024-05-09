package com.example.foodtracker

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object NotificationStorage {

    private const val PREFERENCES_NAME = "notification_preferences"
    private const val KEY_NOTIFICATIONS = "notifications"

    fun saveNotification(context: Context, notificationData: NotificationData) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()

        val notifications = getNotifications(context).toMutableList()
        notifications.add(notificationData)

        val json = gson.toJson(notifications)
        editor.putString(KEY_NOTIFICATIONS, json)
        editor.apply()
    }

    fun getNotifications(context: Context): List<NotificationData> {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val gson = Gson()

        val json = sharedPreferences.getString(KEY_NOTIFICATIONS, null)
        val type = object : TypeToken<List<NotificationData>>() {}.type

        return gson.fromJson(json, type) ?: emptyList()
    }
}