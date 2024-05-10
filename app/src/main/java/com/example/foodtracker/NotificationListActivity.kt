package com.example.foodtracker

// Import necessary packages and classes
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.util.Date

// NotificationListActivity class definition
class NotificationListActivity : AppCompatActivity() {

    // Function called when activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to the layout file activity_notification_list
        setContentView(R.layout.activity_notification_list)

        // Find the ListView by its id
        val listView = findViewById<ListView>(R.id.list_view)

        // Retrieve notifications from NotificationStorage
        val notifications = NotificationStorage.getNotifications(this)

        // Create an adapter to populate the ListView with notification data
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notifications.map { "${it.title} - ${it.content} - ${Date(it.dateTime)}" })

        // Set the adapter for the ListView
        listView.adapter = adapter
    }
}
