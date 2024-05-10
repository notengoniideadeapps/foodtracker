package com.example.foodtracker

// Import necessary packages and classes
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

// NotificationReceiver class definition
class NotificationReceiver : BroadcastReceiver() {

    // Function called when a broadcast is received
    override fun onReceive(context: Context, intent: Intent) {
        // Retrieve notification data from intent extras
        val notificationId = intent.getIntExtra("notificationId", 0)
        val notificationTitle = intent.getStringExtra("notificationTitle")
        val notificationContent = intent.getStringExtra("notificationContent")

        // Define a unique channelId for the notification
        val channelId = "michael"
        // Create notification channel
        createNotificationChannel(context, channelId)

        // Build notification
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_baseline_add_shopping_cart_24)
            .setContentTitle(notificationTitle)
            .setContentText(notificationContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Get notification manager
        val notificationManager = NotificationManagerCompat.from(context)

        // Check if notifications are enabled
        if (notificationManager.areNotificationsEnabled()) {
            // Display the notification
            notificationManager.notify(1234, builder.build())
        } else {
            // Handle the case where notifications are disabled
            Toast.makeText(context, "Please enable notifications in settings", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to create a notification channel
    private fun createNotificationChannel(context: Context, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Define channel name and description
            val name = "Your Channel Name"
            val descriptionText = "Your Channel Description"
            // Define channel importance
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            // Create notification channel
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Get notification manager and create the channel
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
