package com.example.sestudentjuhabitapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class CalenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)

        val selectHabitDropDown = findViewById<Spinner>(R.id.calendar_habit_select_dropdown)
        // Todo: remove exampleStrings and fetch actual habit names from database.
        val exampleStrings = arrayOf("test1", "test3", "cant count?", "hej", "test2")
        val adapter : ArrayAdapter<String> = // Todo: Fill with names from the database.
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, exampleStrings)
        selectHabitDropDown.adapter = adapter

        val calendar = findViewById<CalendarView>(R.id.calendar_calendar_view)
    }
}