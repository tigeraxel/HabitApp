package com.example.sestudentjuhabitapp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
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
        time: String,
        challenger: String
    ) {
        val userReference = database.getReference("users").child(email).child("Habits")
        var newHabit = HabitData(name, days, pushNotification, time, challenger)
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

    fun deleteHabit(nameOfHabit: String) {
        database.getReference("users").child(email).child("Habits").child(nameOfHabit)
            .removeValue()
    }

    fun deleteChallenge(nameOfHabit: String) {
        database.getReference("users").child(email).child("HabitChallenges").child(nameOfHabit)
            .removeValue()
    }

    fun acceptChallenge(nameOfHabit: String) {
        database.getReference("users").child(email).child("HabitChallenges")
            .child(nameOfHabit).get().addOnSuccessListener {
                val reqHabit = it.getValue<HabitData>()
                insertHabit(
                    reqHabit!!.name!!,
                    reqHabit!!.days!!,
                    reqHabit!!.pushNotification!!,
                    reqHabit!!.time!!,
                    reqHabit!!.challenger!!
                )
                database.getReference("users").child(email).child("HabitChallenges")
                    .child(reqHabit.name!!).removeValue()
            }
    }

}