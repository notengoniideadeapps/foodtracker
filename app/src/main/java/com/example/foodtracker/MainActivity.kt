package com.example.foodtracker

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
import android.telephony.SmsManager
import android.util.Log
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity() {

    // Variable to track notification permission
    private val notificationPermissionInitializer =
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

    // popup dialog for notification permission settings
    private fun showSettingDialog() {
        MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setTitle("Notification Permission")
            .setMessage("Notification permission is required to use this application, Please allow notification permission from setting")
            .setPositiveButton("Ok") { _, _ ->
                val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    //  rationale for notification permission
    private fun showNotificationPermissionRationale() {

        MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setTitle("Alert")
            .setMessage("Notification permission is required, to show notification")
            .setPositiveButton("Ok") { _, _ ->
                if (Build.VERSION.SDK_INT >= 33) {
                    notificationPermissionInitializer.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Variable to track if notification permission is granted
    var hasNotificationPermissionGranted = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // If device is running Android 12L (API level 33) or later, request notification permission
        if (Build.VERSION.SDK_INT >= 33) {
            notificationPermissionInitializer.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            // consider notification permission as granted
            hasNotificationPermissionGranted = true
        }

        val calendarButton = findViewById<Button>(R.id.calendarbutton)
        calendarButton.setOnClickListener {
            // Create an intent to navigate to CalendarActivity
            val intent = Intent(this@MainActivity, CalendarView::class.java)
            // Start the CalendarActivity
            startActivity(intent)
        }

        val listButton = findViewById<Button>(R.id.foodbutton)
        listButton.setOnClickListener {
            val intent = Intent(this@MainActivity, NotificationListActivity::class.java)
            startActivity(intent)
        }

        val smsButton = findViewById<Button>(R.id.smsbutton)
        smsButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.sms, null)
            val message = dialogLayout.findViewById<EditText>(R.id.smsedit)
            var number = " "
            val smsMessage = "Hi, I am using food tracker by Michael Awoyemi. Join me!!! "
            val smsManager: SmsManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                smsManager = this.getSystemService(SmsManager::class.java)
            } else {
                smsManager = SmsManager.getDefault()
            }
            with(builder) {
                setTitle("Phone number")
                setPositiveButton("<Send>") { dialog, which ->
                    number = message.text.toString()
                    try {
                        smsManager.sendTextMessage(number, null, smsMessage, null, null)
                        Toast.makeText(applicationContext, "SMS Message Sent!", Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, "SMS failed to send!!", Toast.LENGTH_LONG).show()
                    }
                }
                setNegativeButton("cancel") { dialog, which ->
                    Log.d("Main", "SMS Cacncelled")
                }
                setView(dialogLayout)
                show()
            }

        }

    }
}