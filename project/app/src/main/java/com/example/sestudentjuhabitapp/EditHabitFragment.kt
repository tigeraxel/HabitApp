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
import com.google.firebase.database.ktx.getValue
import java.util.*
import kotlin.properties.Delegates

class EditHabitFragment : Fragment() {
    val habit = HabitClass()
    lateinit var titleOfHabit: EditText
    lateinit var selectMondayBtn: ToggleButton
    lateinit var selectTuesdayBtn: ToggleButton
    lateinit var selectWednesdayBtn: ToggleButton
    lateinit var selectThursdayBtn: ToggleButton
    lateinit var selectFridayBtn: ToggleButton
    lateinit var selectSaturdayBtn: ToggleButton
    lateinit var selectSundayBtn: ToggleButton
    lateinit var pushNotificationBool : Switch
    lateinit var selectTimeButton: Button
    var habitName : String = ""
    // Create variables here to store any desired data on creation. startValue example:
    //var startValue = 0

    private var userTime = "" // Holds the time set by the user.

    override fun onCreateView(
        // Returns a root view object.
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

        ) = inflater.inflate(R.layout.fragment_edit_habit, container, false)!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) { // Needs to find and fill any required views.
        super.onViewCreated(view, savedInstanceState)



        titleOfHabit =
            view?.findViewById(R.id.fragment_habit_name_edittext)
        selectMondayBtn = view?.findViewById(R.id.fragment_monday_button)
        selectTuesdayBtn = view?.findViewById(R.id.fragment_tuesday_button)
        selectWednesdayBtn = view?.findViewById(R.id.fragment_wednesday_button)
        selectThursdayBtn = view?.findViewById(R.id.fragment_thursday_button)
        selectFridayBtn = view?.findViewById(R.id.fragment_friday_button)
        selectSaturdayBtn = view?.findViewById(R.id.fragment_saturday_button)
        selectSundayBtn = view?.findViewById(R.id.fragment_sunday_button)
        pushNotificationBool = view?.findViewById(R.id.fragment_switch)
        selectTimeButton = view.findViewById(R.id.fragment_timepicker_button)


        if(habitName != ""){
            var currentHabit = habit.getHabit(habitName)
                .addOnSuccessListener {
                    val currentHabit = it.getValue<HabitData>()!!
                    titleOfHabit.setText(currentHabit.name)
                    if(currentHabit.days?.get("monday") == true)
                        selectMondayBtn.isChecked = true
                    if(currentHabit.days?.get("tuesday") == true)
                        selectTuesdayBtn.isChecked = true
                    if(currentHabit.days?.get("wednesday") == true)
                        selectWednesdayBtn.isChecked = true
                    if(currentHabit.days?.get("thursday") == true)
                        selectThursdayBtn.isChecked = true
                    if(currentHabit.days?.get("friday") == true)
                        selectFridayBtn.isChecked = true
                    if(currentHabit.days?.get("saturday") == true)
                        selectSaturdayBtn.isChecked = true
                    if(currentHabit.days?.get("sunday") == true)
                        selectSundayBtn.isChecked = true
                    userTime = currentHabit.time!!
                    if(currentHabit.pushNotification == true)
                        pushNotificationBool.isChecked = true
                }
            Log.d("EditHabitFragment", currentHabit.toString())
        }

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
        var titleOfHabitText = titleOfHabit.text.toString()

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

        habit.insertHabit(titleOfHabitText, days, pushNotificationBool.isChecked, userTime, "")
    }

    public fun saveHabitName(name : String){
        habitName = name
    }

    public fun updateHabit(){
        habit.deleteHabit(habitName!!)
        insertToDB()

    }
}

