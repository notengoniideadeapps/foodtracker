package com.example.foodtracker

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class calendarview : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display for immersive experience
        enableEdgeToEdge()

        // Set the content view to the layout file activity_calendarview
        setContentView(R.layout.activity_calendarview)

        // Apply window insets to adjust view padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize DatePicker widget
        val datePicker = findViewById<DatePicker>(R.id.date) as DatePicker

        // Set initial date to current date
        val calendar: Calendar = Calendar.getInstance()
        datePicker.init(calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)) { view, year, monthOfYear, dayOfMonth ->

            // Create an instance of TaskDialogFragment
            val taskDialogFragment = TaskDialogFragment()

            // Pass selected date to TaskDialogFragment
            val args = Bundle()
            args.putInt("year", year)
            args.putInt("month", monthOfYear)
            args.putInt("day", dayOfMonth)

            taskDialogFragment.arguments = args

            // Show TaskDialogFragment
            taskDialogFragment.show(supportFragmentManager, "TaskDialogFragment")
        }
    }
}
