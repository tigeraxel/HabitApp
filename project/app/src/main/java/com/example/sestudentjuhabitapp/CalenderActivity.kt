package com.example.sestudentjuhabitapp

/*
To set which days are selected or not, call the constructor: "CalendarDaysFragment.newInstance(monday, tuesday, wednesday, thursday
                                                                                               friday, saturday, sunday)
Where the days fetched from the database.
 */

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class CalenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)

        // The dropdown dialog, fill with list of habits from DB.
        val exampleStrings = arrayOf("test1", "test3", "cant count?", "hej", "test2")
        val adapter : ArrayAdapter<String> = // Todo: Fill with names from the database.
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, exampleStrings)
        val selectHabitDialog = findViewById<Spinner>(R.id.custom_calendar_spinner_dialog)
        selectHabitDialog.adapter = adapter
        

        // Todo: change all bools below to actual data from the database:
        
        val daysFragment = CalendarDaysFragment // Set which days are selected and which are not:
            .newInstance(true, false, false, true, false, true, true)
        val daysFragment1 = CalendarDaysFragment
            .newInstance(true, false, false, true, false, true, true)
        val daysFragment2 = CalendarDaysFragment
            .newInstance(true, false, false, true, false, true, true)
        val daysFragment3 = CalendarDaysFragment
            .newInstance(true, false, false, true, false, true, true)
        val daysFragment4 = CalendarDaysFragment
            .newInstance(true, false, false, true, false, true, true)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.custom_row1_fragment_container, daysFragment)
            .add(R.id.custom_row2_fragment_container, daysFragment1)
            .add(R.id.custom_row3_fragment_container, daysFragment2)
            .add(R.id.custom_row4_fragment_container, daysFragment3)
            .add(R.id.custom_row5_fragment_container, daysFragment4)
            .commit()
    }
}