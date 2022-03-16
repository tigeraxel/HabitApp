package com.example.sestudentjuhabitapp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HabitClass {

    private var mAuth = FirebaseAuth.getInstance();
    private var userID = mAuth!!.currentUser!!.uid
    private var email = mAuth.currentUser?.email.toString().replace(".", "")
    private val database = Firebase.database


    fun insertHabit(
        name: String,
        days: HashMap<String, Boolean>,
        pushNotification: Boolean,
        time: String
    ) {
        val userReference = database.getReference("users").child(email).child("Habits")
        var newHabit = HabitData(name, days, pushNotification, time, "")
        userReference.child(name).setValue(newHabit)

    }

    fun challengeUser(
        emailChallenger: String,
        name: String,
        days: HashMap<String, Boolean>,
        pushNotification: Boolean,
        time: String
    ) {

        var emailChallengerFixed = emailChallenger.replace(".", "")
        val userReference =
            database.getReference("users").child(emailChallengerFixed).child("HabitChallenges")
        var newHabit = HabitData(name, days, pushNotification, time, email)
        userReference.child(name).setValue(newHabit)

    }

}