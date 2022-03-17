package com.example.sestudentjuhabitapp

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import java.util.*

class EditHabitFragment : Fragment() {
    val habit = HabitClass()
    // Create variables here to store any desired data on creation. startValue example:
    //var startValue = 0

    private var userTime = "" // Holds the time set by the user.

    override fun onCreateView( // Returns a root view object.
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_edit_habit, container, false)!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) { // Needs to find and fill any required views.
        super.onViewCreated(view, savedInstanceState)

        val selectTimeButton = view.findViewById<Button>(R.id.fragment_timepicker_button)
        selectTimeButton?.setOnClickListener {

            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)
            val timePick = TimePickerDialog(
                activity,
                TimePickerDialog.OnTimeSetListener(function = { _, h, m ->
                    if (m > 9)
                        userTime = "$h:$m" // Sets the time to the member variable.
                    else userTime = "$h:0$m"
                }),
                currentHour,
                currentMinute,
                true
            )
            timePick.show()
        }

    }

    public fun insertToDB() {

        val titleOfHabit =
            view?.findViewById<EditText>(R.id.fragment_habit_name_edittext)!!.text.toString()
        val selectMondayBtn = view?.findViewById<ToggleButton>(R.id.fragment_monday_button)
        val selectTuesdayBtn = view?.findViewById<ToggleButton>(R.id.fragment_tuesday_button)
        val selectWednesdayBtn = view?.findViewById<ToggleButton>(R.id.fragment_wednesday_button)
        val selectThursdayBtn = view?.findViewById<ToggleButton>(R.id.fragment_thursday_button)
        val selectFridayBtn = view?.findViewById<ToggleButton>(R.id.fragment_friday_button)
        val selectSaturdayBtn = view?.findViewById<ToggleButton>(R.id.fragment_saturday_button)
        val selectSundayBtn = view?.findViewById<ToggleButton>(R.id.fragment_sunday_button)

        val pushNotificationBool = view?.findViewById<Switch>(R.id.fragment_switch)!!.isChecked

        var days = HashMap<String, Boolean>()
        if (selectMondayBtn!!.isChecked())
            days.put("monday", true)
        if (selectTuesdayBtn!!.isChecked())
            days.put("tuesday", true)
        if (selectWednesdayBtn!!.isChecked())
            days.put("wednesday", true)
        if (selectThursdayBtn!!.isChecked())
            days.put("thursday", true)
        if (selectFridayBtn!!.isChecked())
            days.put("friday", true)
        if (selectSaturdayBtn!!.isChecked())
            days.put("saturday", true)
        if (selectSundayBtn!!.isChecked())
            days.put("sunday", true)

        habit.insertHabit(titleOfHabit, days, pushNotificationBool, userTime,"")


    }
}

