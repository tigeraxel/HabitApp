package com.example.sestudentjuhabitapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentManager
import java.util.*

class NewHabitActivity : AppCompatActivity() {
    private val validation = ValidationClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_habit)

        val fm: FragmentManager = supportFragmentManager

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
}