package com.example.foodtracker

// Import necessary packages and classes
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// MainActivity class definition
class MainActivity : AppCompatActivity() {

    // Variable to track notification permission
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasNotificationPermissionGranted = isGranted
            if (!isGranted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Build.VERSION.SDK_INT >= 33) {
                        if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                            showNotificationPermissionRationale()
                        } else {
                            showSettingDialog()
                        }
                    }
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "notification permission granted",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            // Check if notification permissions are granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val isEnabled = notificationManager.areNotificationsEnabled()

                if (!isEnabled) {
                    // Open the app notification settings if notifications are not enabled
                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    startActivity(intent)

                    return@registerForActivityResult
                }
            } else {
                val areEnabled = NotificationManagerCompat.from(this).areNotificationsEnabled()

                if (!areEnabled) {
                    // Open the app notification settings if notifications are not enabled
                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    startActivity(intent)

                    return@registerForActivityResult
                }
            }
        }


    // Function to show dialog for notification permission settings
    private fun showSettingDialog() {
        MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setTitle("Notification Permission")
            .setMessage("Notification permission is required, Please allow notification permission from setting")
            .setPositiveButton("Ok") { _, _ ->
                val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Function to show rationale for notification permission
    private fun showNotificationPermissionRationale() {

        MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setTitle("Alert")
            .setMessage("Notification permission is required, to show notification")
            .setPositiveButton("Ok") { _, _ ->
                if (Build.VERSION.SDK_INT >= 33) {
                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Variable to track if notification permission is granted
    var hasNotificationPermissionGranted = false

    // Function called when activity is created
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable edge-to-edge display for immersive experience
        enableEdgeToEdge()
        // Set the content view to the layout file activity_main
        setContentView(R.layout.activity_main)

        // If device is running Android 12L (API level 33) or later, request notification permission
        if (Build.VERSION.SDK_INT >= 33) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            // If device is running earlier version of Android, consider notification permission as granted
            hasNotificationPermissionGranted = true
        }

        // Find the calendar button by its id
        val calendarButton = findViewById<Button>(R.id.calendarbutton)
        // Set a click listener for the calendar button
        calendarButton.setOnClickListener {
            // Create an intent to navigate to CalendarActivity
            val intent = Intent(this@MainActivity, CalendarView::class.java)
            // Start the CalendarActivity
            startActivity(intent)
        }

        // Find the notification button by its id
        val notificationButton = findViewById<Button>(R.id.foodbutton)
        // Set a click listener for the notification button
        notificationButton.setOnClickListener {
            // Create an intent to navigate to NotificationActivity
            val intent = Intent(this@MainActivity, NotificationListActivity::class.java)
            // Start the NotificationActivity
            startActivity(intent)
        }
    }
}