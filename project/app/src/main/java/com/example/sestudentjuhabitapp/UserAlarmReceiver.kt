package com.example.sestudentjuhabitapp


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

const val placeholderAlarmNotificationText = "Notification Text"
const val placeholderAlarmHabitName = "Habit Name"

// This class listens for alarms set by the user, when one goes off, it fires a system notification to the users device.
class UserAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent?.action == placeholderAlarmHabitName){
            displayHabitNotification(placeholderAlarmHabitName, placeholderAlarmNotificationText, context!!)
        }
    }
}