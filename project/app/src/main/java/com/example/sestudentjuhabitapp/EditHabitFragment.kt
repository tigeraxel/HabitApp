package com.example.sestudentjuhabitapp

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.DialogFragment
import java.util.*
import kotlin.collections.HashMap
import android.widget.TimePicker




/*
Fragment used to create and edit an habit.

To use the fragment outside of xml implementations, call the class method: newInstance()

val a = EditHabitFragment() This would work, but not compatible with runtime config changes.

The helper initializer, newInstance(), allows for the use of Bundles, which will survive runtime config changes.

To send data to the fragment's Bundle:

fun newInstance(data : data type) = EditHabitFragment().apply{
    arguments = Bundle().apply{
        putDatatype("key", data)
    }
}
 */

class EditHabitFragment : Fragment() {
    val habit = HabitClass()
    // Create variables here to store any desired data on creation. startValue example:
    //var startValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // startValue = requireArguments().getInt("key") // Example



        // Todo: Add to habit class / or firebase.
    }

    override fun onCreateView( // Returns a root view object.
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_edit_habit, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Needs to find and fill any required views.
        super.onViewCreated(view, savedInstanceState)

        //val editText = view.findViewById<EditText>(R.id.exampleId) // Example
        // editText.text // Example

    }

    public fun insertToDB(){

        val titleOfHabit = view?.findViewById<EditText>(R.id.fragment_habit_name_edittext)!!.text.toString()
        val selectMondayBtn = view?.findViewById<ToggleButton>(R.id.fragment_monday_button)
        val selectTuesdayBtn = view?.findViewById<ToggleButton>(R.id.fragment_tuesday_button)
        val selectWednesdayBtn = view?.findViewById<ToggleButton>(R.id.fragment_wednesday_button)
        val selectThursdayBtn = view?.findViewById<ToggleButton>(R.id.fragment_thursday_button)
        val selectFridayBtn = view?.findViewById<ToggleButton>(R.id.fragment_friday_button)
        val selectSaturdayBtn = view?.findViewById<ToggleButton>(R.id.fragment_saturday_button)
        val selectSundayBtn = view?.findViewById<ToggleButton>(R.id.fragment_sunday_button)

        val pushNotificationBool = view?.findViewById<Switch>(R.id.fragment_switch)!!.isChecked
        val selectTimeButton = view?.findViewById<ToggleButton>(R.id.fragment_sunday_button)
        var time = ""

        val newCalendar = Calendar.getInstance()
        selectTimeButton!!.setOnClickListener(){
        }

        val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)

        var days = HashMap<String, Boolean> ()
        if(selectMondayBtn!!.isChecked())
        days.put("monday" , true)
        if(selectTuesdayBtn!!.isChecked())
        days.put("tuesday" , true)
        if(selectWednesdayBtn!!.isChecked())
            days.put("wednesday" , true)
        if(selectThursdayBtn!!.isChecked())
            days.put("thursday" , true)
        if(selectFridayBtn!!.isChecked())
            days.put("friday" , true)
        if(selectSaturdayBtn!!.isChecked())
            days.put("saturday" , true)
        if(selectSundayBtn!!.isChecked())
            days.put("sunday" , true)

        habit.insertHabit(titleOfHabit, days, pushNotificationBool, time)
    }
}

