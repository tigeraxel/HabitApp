package com.example.sestudentjuhabitapp

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // : TimePickerDialog // Todo: wtf do you want, why wont you allow implicit or explicit?

        // Set the time to current time:
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        // Creates a new TimePicker, which starts at the current time. Not supposed to be AM / PM but todo: currently is..
        TimePickerDialog(activity, this, currentHour, currentMinute, DateFormat.is24HourFormat(activity))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_time_picker, container, false)

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) { // Save the specified time to the relevant habit.
        TODO("Not yet implemented")
    }
}
