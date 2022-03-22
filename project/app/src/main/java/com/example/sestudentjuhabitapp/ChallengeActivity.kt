package com.example.sestudentjuhabitapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ChallengeActivity : AppCompatActivity() {
    val maxHabitTitleLength = 24
    val minHabitTitleLength = 2
    val maxEmailLength = 34
    val minEmailLength = 6

    val habit = HabitClass()
    val validation = ValidationClass()


    private val database = Firebase.database
    private var currentUserEmail = Firebase.auth.currentUser!!.email.toString().replace(".", "")

    lateinit var submitButton: Button
    lateinit var habitName: EditText
    lateinit var userChallenged: EditText

    lateinit var habitNameText: String
    lateinit var userChallengedText: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        submitButton = findViewById(R.id.btnSubmit)
        habitName = findViewById(R.id.habitNameEditText)
        userChallenged = findViewById(R.id.challengedUser)


        submitButton.setOnClickListener {
            userChallengedText = userChallenged.text.toString().replace(".", "")
            habitNameText = habitName.text.toString()
            val errors = returnValidationErrors()
            if (errors.isEmpty()) {
                database.getReference("users").child(currentUserEmail).child("Habits")
                    .child(habitNameText).get().addOnSuccessListener {
                        val reqHabit = it.getValue<HabitData>()
                        if (reqHabit != null) {
                            habit.challengeUser(
                                userChallengedText,
                                reqHabit!!.name!!,
                                reqHabit!!.days!!,
                                reqHabit!!.pushNotification!!,
                                reqHabit!!.time!!
                            )
                            finish()
                        } else {
                            var tempErrorArray = ArrayList<String>()
                            tempErrorArray.add("Habit doesnt exist")
                            validation.showValidationErrors(tempErrorArray, this)
                        }

                    }
            } else {
                validation.showValidationErrors(errors, this)
            }
        }
    }

    fun returnValidationErrors(): ArrayList<String> {
        var errorsArray = ArrayList<String>()
        if (userChallenged.text.length > maxEmailLength)
            errorsArray.add(
                getString(R.string.the_email_should_be_max) + " " + maxEmailLength + " " + getString(
                    R.string.characters
                ) + "\n"
            )
        if (userChallenged.text.length < minEmailLength)
            errorsArray.add(
                getString(R.string.the_email_should_be_min) + " " + minEmailLength + " " + getString(
                    R.string.characters
                ) + "\n"
            )
        if (habitName.text.length > maxHabitTitleLength)
            errorsArray.add(
                getString(R.string.the_title_of_the_habit_should_max_be) + " " + maxHabitTitleLength + " " + getString(
                    R.string.characters
                ) + "\n"
            )
        if (habitName.text.length < minHabitTitleLength)
            errorsArray.add(
                getString(R.string.the_title_of_the_habit_should_min_be) + " " + minHabitTitleLength + " " + getString(
                    R.string.characters
                ) + "\n"
            )

        return errorsArray

    }
}



