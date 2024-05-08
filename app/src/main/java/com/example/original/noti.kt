package com.example.original

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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.lang.StringBuilder

class noti : AppCompatActivity() {

    lateinit var  notificationManager: NotificationManager
    lateinit var  notificationChannel: NotificationChannel
    lateinit var  builder: Notification.Builder
    private val channelID= "michael"
    private val desc="Notifications"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_noti)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                notificationChannel=NotificationChannel(channelID,desc,NotificationManager.IMPORTANCE_HIGH)

                notificationChannel.enableLights(true)
                notificationChannel.lightColor=Color.BLUE
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)

                builder=Notification.Builder(this)
                    .setContentTitle("Food notification")
                    .setContentText("Buy more food")
                    .setSmallIcon(R.drawable.ic_baseline_add_shopping_cart_24)
                    .setChannelId(channelID)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)
            }
            else {

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    builder=Notification.Builder(this)
                        .setContentTitle("Food notification")
                        .setContentText("Buy more food")
                        .setSmallIcon(R.drawable.ic_baseline_add_shopping_cart_24)
                        .setChannelId(channelID)
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                        .setContentIntent(pendingIntent)
                }
            }
            notificationManager.notify(1234,builder.build())
        }
    }
}