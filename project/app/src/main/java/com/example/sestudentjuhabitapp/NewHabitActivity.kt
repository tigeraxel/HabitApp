package com.example.sestudentjuhabitapp

import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.*

const val MAX_INPUT_SIZE = 50 // The maximum length of the name of a habit, arbitrary number.

class NewHabitActivity : AppCompatActivity() {
    val validation = ValidationClass()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_habit)


        //val editHabitNameEditText = findViewById<EditText>(R.id.fragment_habit_name_edittext)
        //val habitName = editHabitNameEditText!!.editableText // Fetch the text from the window.
        //if(habitName.isEmpty() || habitName.length > MAX_INPUT_SIZE)
        //displayInputErrorMessage()
        val fm: FragmentManager = supportFragmentManager
//if you added fragment via layout xml

        val createHabitButton = findViewById<Button>(R.id.create_create_habit_button)
        createHabitButton.setOnClickListener {
            val fragment: EditHabitFragment =
                fm.findFragmentById(R.id.create_fragment_container_view) as EditHabitFragment
            var errors: ArrayList<String> = fragment.returnValidationErrors()
            if(errors.isEmpty()) {
                fragment.insertToDB()
                finish()
            }
            else{
                validation.showValidationErrors(errors,this)
            }
        }
    }


//if you added fragment via layout xml




}

