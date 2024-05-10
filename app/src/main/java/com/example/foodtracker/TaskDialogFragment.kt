package com.example.foodtracker

// Import necessary packages and classes
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.util.*

// TaskDialogFragment class definition
class TaskDialogFragment : DialogFragment() {

    // Function called when creating the dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create an AlertDialog builder
        val builder = AlertDialog.Builder(requireActivity())

        // Get layout inflater
        val inflater = requireActivity().layoutInflater
        // Inflate the dialog layout
        val view = inflater.inflate(R.layout.dialog_layout, null)

        // Find views in the dialog layout
        val timePickerButton = view.findViewById<Button>(R.id.time_button)
        val taskNameEditText = view.findViewById<EditText>(R.id.task_name)
        val taskDescriptionEditText = view.findViewById<EditText>(R.id.task_description)

        // Get current date from Calendar instance
        val calendar = Calendar.getInstance()
        // Get year, month, and day from arguments, or use current date
        val year = arguments?.getInt("year") ?: calendar.get(Calendar.YEAR)
        val month = arguments?.getInt("month") ?: calendar.get(Calendar.MONTH)
        val day = arguments?.getInt("day") ?: calendar.get(Calendar.DAY_OF_MONTH)

        // Set click listener for time picker button
        timePickerButton.setOnClickListener {
            // Get current time
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            // Create a TimePickerDialog
            val tpd = TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                // Set selected time to calendar instance
                calendar.set(year, month, day, hourOfDay, minute)
                // Update text of time picker button
                timePickerButton.text = "" + hourOfDay + ":" + minute
            }, hour, minute, true)

            // Show the TimePickerDialog
            tpd.show()
        }

        // Configure the AlertDialog
        builder.setView(view)
            .setPositiveButton("Save",
                DialogInterface.OnClickListener { dialog, id ->
                    // Get task name and description from EditTexts
                    val taskName = taskNameEditText.text.toString()
                    val taskDescription = taskDescriptionEditText.text.toString()
                    // Create a NotificationData instance with task details
                    val notificationData = NotificationData(0, taskName, taskDescription, calendar.timeInMillis)
                    // Schedule the notification
                    NotificationUtils.scheduleNotification(requireContext(), notificationData)
                    // Save the notification data
                    NotificationStorage.saveNotification(requireContext(), notificationData)
                })
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, id ->
                    // Cancel the dialog
                    getDialog()?.cancel()
                })

        // Create and return the AlertDialog
        return builder.create()
    }
}
