package com.example.sestudentjuhabitapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ChallengeActivity : AppCompatActivity() {
    val habit = HabitClass()
    private val database = Firebase.database
    private var currentUserEmail = Firebase.auth.currentUser!!.email.toString().replace(".", "")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        val submitButton = findViewById<Button>(R.id.btnSubmit)
        var habitName = findViewById<EditText>(R.id.habitNameEditText)

        submitButton.setOnClickListener {

            val userChallenged = findViewById<EditText>(R.id.challengedUser).text.toString()
            var habitText = habitName.text.toString()
            database.getReference("users").child(currentUserEmail).child("Habits")
                .child(habitText).get().addOnSuccessListener {
                    val reqHabit = it.getValue<HabitData>()
                    if (reqHabit != null) {
                        habit.challengeUser(
                            userChallenged,
                            reqHabit!!.name!!,
                            reqHabit!!.days!!,
                            reqHabit!!.pushNotification!!,
                            reqHabit!!.time!!
                        )
                        finish()
                    }

                }
        }
    }
}
