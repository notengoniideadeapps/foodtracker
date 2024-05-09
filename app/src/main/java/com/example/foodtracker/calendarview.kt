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
        enableEdgeToEdge()
        setContentView(R.layout.activity_calendarview)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val datePicker = findViewById<DatePicker>(R.id.date) as DatePicker

        val calendar: Calendar = Calendar.getInstance()
        datePicker.init(calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)) { view, year, monthOfYear, dayOfMonth ->
            val taskDialogFragment = TaskDialogFragment()

            val args = Bundle()
            args.putInt("year", year)
            args.putInt("month", monthOfYear)
            args.putInt("day", dayOfMonth)

            taskDialogFragment.arguments = args
            taskDialogFragment.show(supportFragmentManager, "TaskDialogFragment")
        }
    }
}
