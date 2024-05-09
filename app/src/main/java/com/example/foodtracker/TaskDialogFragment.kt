package com.example.foodtracker

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.util.*

class TaskDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_layout, null)
        val timePickerButton = view.findViewById<Button>(R.id.time_button)
        val taskNameEditText = view.findViewById<EditText>(R.id.task_name)
        val taskDescriptionEditText = view.findViewById<EditText>(R.id.task_description)

        val calendar = Calendar.getInstance()
        val year = arguments?.getInt("year") ?: calendar.get(Calendar.YEAR)
        val month = arguments?.getInt("month") ?: calendar.get(Calendar.MONTH)
        val day = arguments?.getInt("day") ?: calendar.get(Calendar.DAY_OF_MONTH)

        timePickerButton.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.set(year, month, day, hourOfDay, minute)
                timePickerButton.text = "" + hourOfDay + ":" + minute
            }, hour, minute, true)

            tpd.show()
        }

        builder.setView(view)
            .setPositiveButton("Save",
                DialogInterface.OnClickListener { dialog, id ->
                    val taskName = taskNameEditText.text.toString()
                    val taskDescription = taskDescriptionEditText.text.toString()
                    val notificationData = NotificationData(0, taskName, taskDescription, calendar.timeInMillis)
                    NotificationUtils.scheduleNotification(requireContext(), notificationData)
                    NotificationStorage.saveNotification(requireContext(), notificationData)
                })
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, id ->
                    getDialog()?.cancel()
                })
        return builder.create()
    }
}
