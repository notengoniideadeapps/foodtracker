package com.example.foodtracker

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object NotificationStorage {

    // Constants for shared preferences
    private const val PREFERENCES_NAME = "notification_preferences"
    private const val KEY_NOTIFICATIONS = "notifications"

    fun saveNotification(context: Context, notificationData: NotificationData) {
        // Get SharedPreferences instance
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()

        // Retrieve existing notifications from SharedPreferences
        val notifications = getNotifications(context).toMutableList()
        // Add new notification to the list
        notifications.add(notificationData)

        // Convert notifications list to JSON
        val json = gson.toJson(notifications)
        // Save JSON string to SharedPreferences
        editor.putString(KEY_NOTIFICATIONS, json)
        editor.apply()
    }

    fun getNotifications(context: Context): List<NotificationData> {
        // Get SharedPreferences instance
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val gson = Gson()

        // Retrieve JSON string of notifications from SharedPreferences
        val json = sharedPreferences.getString(KEY_NOTIFICATIONS, null)
        val type = object : TypeToken<List<NotificationData>>() {}.type

        return gson.fromJson(json, type) ?: emptyList()
    }
}
