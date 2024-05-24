package com.example.foodtracker

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CalendarView : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calendarview)
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
            val selectedDate = Bundle()
            selectedDate.putInt("year", year)
            selectedDate.putInt("month", monthOfYear)
            selectedDate.putInt("day", dayOfMonth)

            taskDialogFragment.arguments = selectedDate
            // Show TaskDialogFragment
            taskDialogFragment.show(supportFragmentManager, "TaskDialogFragment")
        }
    }
}
