package com.example.sestudentjuhabitapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.firebase.database.ktx.getValue
import java.util.*


class EditHabitFragment : Fragment() {
    val validation = ValidationClass()
    val maxHabitTitleLength = validation.maxHabitTitleLength
    val minHabitTitleLength = validation.minHabitTitleLength

    var calendar = Calendar.getInstance()

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
            view.findViewById(R.id.fragment_habit_name_edittext)

        selectMondayBtn = view.findViewById(R.id.fragment_monday_button)
        selectTuesdayBtn = view.findViewById(R.id.fragment_tuesday_button)
        selectWednesdayBtn = view.findViewById(R.id.fragment_wednesday_button)
        selectThursdayBtn = view.findViewById(R.id.fragment_thursday_button)
        selectFridayBtn = view.findViewById(R.id.fragment_friday_button)
        selectSaturdayBtn = view.findViewById(R.id.fragment_saturday_button)
        selectSundayBtn = view.findViewById(R.id.fragment_sunday_button)
        pushNotificationBool = view.findViewById(R.id.fragment_switch)
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

        selectTimeButton.setOnClickListener {

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

        val toggleNotificationsSwitch = view.findViewById<Switch>(R.id.fragment_switch)

        val userAlarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmReceiverIntent = Intent(context, UserAlarmReceiver::class.java)
        alarmReceiverIntent.action = "habit"
        val setAlarmIntent = PendingIntent.getBroadcast(context, 0, alarmReceiverIntent, 0)

        toggleNotificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {

                // Since the user time is stored as a string, substring out the hour and minute values:
                val userAlarmTimeHours = userTime.substringBefore(":").toInt()
                val userAlarmTimeMinutes = userTime.substringAfter(":").toInt()

                // Setup the time of the alarm:
                calendar.apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, userAlarmTimeHours) // At which hour should the alarm go off?
                    set(Calendar.MINUTE, userAlarmTimeMinutes) // Which minute.
                }

                // Check which way the alarm should be implemented, based on Android version installed:
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                    userAlarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(calendar.timeInMillis, setAlarmIntent),
                        setAlarmIntent
                    )
                }
                else{
                    userAlarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis, // The time until the alarm goes off.
                        setAlarmIntent // Which BroadcastReceiver should be called.
                    )
                }
            }
            else{ // Cancel the alarm.
                userAlarmManager.cancel(setAlarmIntent)
            }
        }
    }

    public fun insertToDB() {
        var titleOfHabitText = titleOfHabit.text.toString()

        var days = returnDays()

        habit.insertHabit(titleOfHabitText, days, pushNotificationBool.isChecked, userTime, "")
    }

    public fun saveHabitName(name : String){
        habitName = name
    }

    public fun updateHabit(){
        habit.deleteHabit(habitName!!)
        insertToDB()
    }

    public fun returnValidationErrors() : ArrayList<String>{
        var errorsArray = ArrayList<String>()
        if(titleOfHabit.text.length > maxHabitTitleLength)
            errorsArray.add(getString(R.string.title_too_long) + " " + maxHabitTitleLength.toString() + " " + getString(
                            R.string.characters)  + "\n")

        if(titleOfHabit.text.length < minHabitTitleLength)
            errorsArray.add(getString(R.string.title_too_short) + " " + minHabitTitleLength.toString() + " " + getString(
                R.string.characters) + "\n")

        var days = returnDays()

        if(days.isEmpty())
            errorsArray.add(getString(R.string.choose_a_day) + "\n")

        if(userTime == "")
            errorsArray.add(getString(R.string.choose_a_time) + "\n" )

        return errorsArray
    }

    fun returnDays() : HashMap<String, Boolean>{
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

        return days
    }
}
