package com.example.foodtracker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.lang.StringBuilder

class noti : AppCompatActivity() {

    private lateinit var  notificationManager: NotificationManager
    private lateinit var  notificationChannel: NotificationChannel
    private lateinit var  builder: NotificationCompat.Builder
    private val channelID= "michael"
    private val desc="Notifications"

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_noti)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        createNotificationChannel()


        val btn=findViewById<Button>(R.id.btn);
        notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent=Intent(this, noti2::class.java)


        btn.setOnClickListener {
            val pendingIntent = PendingIntent.getActivity(
                this@noti,
                0, // requestCode
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val view=RemoteViews(packageName, R.layout.activity_noti2)
            builder=NotificationCompat.Builder(this)
                .setContentTitle("Food notification")
                .setContentText("Buy more food")
                .setSmallIcon(R.drawable.ic_baseline_add_shopping_cart_24)
                .setChannelId(channelID)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                .setContentIntent(pendingIntent)


            val notificationManager = NotificationManagerCompat.from(this)

            if (notificationManager.areNotificationsEnabled()) {
                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(1234, builder.build())
            } else {
                // Handle the case where the user has disabled notifications for your app
                Toast.makeText(this, "Please enable notifications in settings", Toast.LENGTH_SHORT).show()
            }
        }
    }
}