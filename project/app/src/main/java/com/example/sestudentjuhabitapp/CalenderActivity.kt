package com.example.sestudentjuhabitapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class CalenderActivity : AppCompatActivity() {

    val habit = HabitClass()
    private var mAuth = FirebaseAuth.getInstance();
    private val database = Firebase.database
    private var email = mAuth.currentUser?.email.toString().replace(".", "")
    lateinit var habitsRef: DatabaseReference
    lateinit var habitList: MutableList<HabitData>

    private var mondayState = false
    private var tuesdayState = false
    private var wednesdayState = false
    private var thursdayState = false
    private var fridayState = false
    private var saturdayState = false
    private var sundayState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)

        val habitName = intent.getStringExtra(habitNamePlaceholder) // Find which habit was selected.

        var currentHabit = habit.getHabit(habitName!!) // Check which days are selected:
            .addOnSuccessListener {
                val currentHabit = it.getValue<HabitData>()!!
                if (currentHabit.days?.get(monday) == true)
                    mondayState = true
                if (currentHabit.days?.get(tuesday) == true)
                    tuesdayState = true
                if (currentHabit.days?.get(wednesday) == true)
                    wednesdayState = true
                if (currentHabit.days?.get(thursday) == true)
                    thursdayState = true
                if (currentHabit.days?.get(friday) == true)
                    fridayState = true
                if (currentHabit.days?.get(saturday) == true)
                    saturdayState = true
                if (currentHabit.days?.get(sunday) == true)
                    sundayState = true

                val daysFragment =
                    CalendarDaysFragment // Set which days to be displayed as selected and which are not:
                        .newInstance(
                            mondayState,
                            tuesdayState,
                            wednesdayState,
                            thursdayState,
                            fridayState,
                            saturdayState,
                            sundayState
                        )
                val daysFragment1 = CalendarDaysFragment
                    .newInstance(
                        mondayState,
                        tuesdayState,
                        wednesdayState,
                        thursdayState,
                        fridayState,
                        saturdayState,
                        sundayState
                    )
                val daysFragment2 = CalendarDaysFragment
                    .newInstance(
                        mondayState,
                        tuesdayState,
                        wednesdayState,
                        thursdayState,
                        fridayState,
                        saturdayState,
                        sundayState
                    )
                val daysFragment3 = CalendarDaysFragment
                    .newInstance(
                        mondayState,
                        tuesdayState,
                        wednesdayState,
                        thursdayState,
                        fridayState,
                        saturdayState,
                        sundayState
                    )
                val daysFragment4 = CalendarDaysFragment
                    .newInstance(
                        mondayState,
                        tuesdayState,
                        wednesdayState,
                        thursdayState,
                        fridayState,
                        saturdayState,
                        sundayState
                    )

                supportFragmentManager // Create all the fragments, with the correct days highlighted:
                    .beginTransaction()
                    .add(R.id.custom_row1_fragment_container, daysFragment)
                    .add(R.id.custom_row2_fragment_container, daysFragment1)
                    .add(R.id.custom_row3_fragment_container, daysFragment2)
                    .add(R.id.custom_row4_fragment_container, daysFragment3)
                    .add(R.id.custom_row5_fragment_container, daysFragment4)
                    .commit()
            }
    }
}