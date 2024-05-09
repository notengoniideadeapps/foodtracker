package com.example.foodtracker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.util.Date

class NotificationListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_list)

        val listView = findViewById<ListView>(R.id.list_view)
        val notifications = NotificationStorage.getNotifications(this)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notifications.map { "${it.title} - ${it.content} - ${Date(it.dateTime)}" })
        listView.adapter = adapter
    }
}