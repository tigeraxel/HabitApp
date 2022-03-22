package com.example.sestudentjuhabitapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class CalendarDaysFragment : Fragment() {

    private var mondayState = false
    private var tuesdayState = false
    private var wednesdayState = false
    private var thursdayState = false
    private var fridayState = false
    private var saturdayState = false
    private var sundayState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mondayState = requireArguments().getBoolean("monday")
        tuesdayState = requireArguments().getBoolean("tuesday")
        wednesdayState = requireArguments().getBoolean("wednesday")
        thursdayState = requireArguments().getBoolean("thursday")
        fridayState = requireArguments().getBoolean("friday")
        saturdayState = requireArguments().getBoolean("saturday")
        sundayState = requireArguments().getBoolean("sunday")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_calendar_days, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!mondayState) {
            val mondayBtn = view.findViewById<Button>(R.id.calendar_fragment_monday_button)
            mondayBtn.setBackgroundColor(R.color.HabitColorSecondary.toInt())
        }
        if (!tuesdayState) {
            val tuesdayBtn = view.findViewById<Button>(R.id.calendar_fragment_tuesday_button)
                .setBackgroundColor(R.color.HabitColorSecondary.toInt())
        }
        if (!wednesdayState) {
            val wednesdayBtn = view.findViewById<Button>(R.id.calendar_fragment_wednesday_button)
                .setBackgroundColor(R.color.HabitColorSecondary.toInt())
        }
        if (!thursdayState) {
            val thursdayBtn = view.findViewById<Button>(R.id.calendar_fragment_thursday_button)
                .setBackgroundColor(R.color.HabitColorSecondary.toInt())
        }
        if (!fridayState) {
            val fridayBtn = view.findViewById<Button>(R.id.calendar_fragment_friday_button)
                .setBackgroundColor(R.color.HabitColorSecondary.toInt())
        }
        if (!saturdayState) {
            val saturdayBtn = view.findViewById<Button>(R.id.calendar_fragment_saturday_button)
                .setBackgroundColor(R.color.HabitColorSecondary.toInt())
            //saturdayBtn.visibility = View.INVISIBLE // Set to invisible
        }
        if (!sundayState) {
            val sundayBtn = view.findViewById<Button>(R.id.calendar_fragment_sunday_button)
                .setBackgroundColor(R.color.HabitColorSecondary.toInt())
        }
    }

    companion object {
        fun newInstance(
            mon: Boolean,
            tues: Boolean,
            wed: Boolean,
            thur: Boolean,
            fri: Boolean,
            sat: Boolean,
            sun: Boolean
        ) = CalendarDaysFragment().apply {
            arguments = Bundle().apply { // Create a Bundle
                putBoolean("monday", mon) // Put stuff into the Bundle.
                putBoolean("tuesday", tues)
                putBoolean("wednesday", wed)
                putBoolean("thursday", thur)
                putBoolean("friday", fri)
                putBoolean("saturday", sat)
                putBoolean("sunday", sun)
            }
        }
    }
}