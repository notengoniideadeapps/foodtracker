package com.example.original

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.createSavedStateHandle

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val datePi = findViewById<DatePicker>(R.id.date) as DatePicker

        val calendar: Calendar = Calendar.getInstance()

        datePi.init(calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))

        { view, year, monthOfYear, dayOfMonth ->
            Toast.makeText(
                applicationContext,
                "#" + datePi.year + "-" + datePi.month + "-" + datePi.dayOfMonth + "/",
                Toast.LENGTH_SHORT
            ).show()

        }

        val notificationButton = findViewById<Button>(R.id.notification_button)
        notificationButton.setOnClickListener {
            // Create an intent to navigate to NotificationActivity
            val intent = Intent(this@MainActivity, noti::class.java)
            // Start the NotificationActivity
            startActivity(intent)
        }

    }
}