package com.example.sestudentjuhabitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnCreateHabitActivity = findViewById<Button>(R.id.btnCreateHabitActivity)
        val btnChallengeActivity = findViewById<Button>(R.id.btnChallengeActivity)
        val btnCalenderActivity = findViewById<Button>(R.id.btnCalendarActivity)
        val btnSettingsActivity = findViewById<ImageButton>(R.id.btnSettingsActivity)


        btnCreateHabitActivity.setOnClickListener {
            val intent = Intent(this, NewHabitActivity::class.java)
            startActivity(intent)
        }

        btnChallengeActivity.setOnClickListener {
            val intent = Intent(this, ChallengeActivity::class.java)
            startActivity(intent)
        }
        btnCalenderActivity.setOnClickListener {
            val intent = Intent(this, CalenderActivity::class.java)
            startActivity(intent)
        }
        btnSettingsActivity.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
