package com.example.sestudentjuhabitapp


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.jar.Manifest

const val placeholderAlarmNotificationText = "Placeholder"
const val placeholderAlarmHabitName = "Placeholder"

// This class listens for alarms set by the user, when one goes off, it fires a system notification to the users device.
class UserAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent?.action == placeholderAlarmHabitName){
            displayHabitNotification(placeholderAlarmHabitName, placeholderAlarmNotificationText)
        }
    }
}