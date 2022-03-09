package com.example.sestudentjuhabitapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

const val MAX_INPUT_SIZE = 50 // The maximum length of the name of a habit, arbitrary number.

class NewHabitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_habit)

        val editHabitNameEditText = findViewById<EditText>(R.id.fragment_habit_name_edittext)
        val habitName = editHabitNameEditText?.editableText.toString() // Fetch the text from the window.
        if(habitName.isEmpty() || habitName.length > MAX_INPUT_SIZE)
            displayInputErrorMessage()

        val createHabitButton = findViewById<Button>(R.id.create_create_habit_button)
        createHabitButton.setOnClickListener{/* todo save habit to database */}

    }

    private fun displayInputErrorMessage(){ // Debug: safe to use, no crashes.

        val displayErrorDialog = AlertDialog.Builder(this)
        displayErrorDialog.setCancelable(false)
            .setNegativeButton(R.string.ok, DialogInterface.OnClickListener{
                dialog, _ -> dialog.cancel()
            })
            .setMessage(R.string.input_string_error_description)

        val alert = displayErrorDialog.create()
        alert.setTitle(R.string.input_string_error_title)
        alert.show()
    }
}