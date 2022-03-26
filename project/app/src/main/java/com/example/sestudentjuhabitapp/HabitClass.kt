package com.example.sestudentjuhabitapp

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class HabitClass {

    private var mAuth = FirebaseAuth.getInstance();
    private var userID = mAuth!!.currentUser!!.uid
    private var email = mAuth.currentUser?.email.toString().replace(".", "")
    private val database = Firebase.database

    fun getHabit(name: String): Task<DataSnapshot> {
        var currentHabit = HabitData()
        return database.getReference(databaseUsersPath).child(email).child(databaseHabitsPathString).child(name).get()
    }

    fun insertHabit(
        name: String,
        days: HashMap<String, Boolean>,
        pushNotification: Boolean,
        time: String,
        challenger: String
    ) {
        val habitsReference = database.getReference(databaseUsersPath).child(email).child(databaseHabitsPathString)
        var newHabit = HabitData(name, days, pushNotification, time, challenger)
        habitsReference.child(name).setValue(newHabit)

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
            database.getReference(databaseUsersPath).child(emailChallengerFixed).child(databaseChallengesPathString)
        var newHabit = HabitData(name, days, pushNotification, time, email)
        userReference.child(name).setValue(newHabit)

    }

    fun deleteHabit(nameOfHabit: String) {
        database.getReference(databaseUsersPath).child(email).child(databaseHabitsPathString).child(nameOfHabit)
            .removeValue()
    }

    fun deleteChallenge(nameOfHabit: String) {
        database.getReference(databaseUsersPath).child(email).child(databaseChallengesPathString).child(nameOfHabit)
            .removeValue()
    }

    fun acceptChallenge(nameOfHabit: String) {
        database.getReference(databaseUsersPath).child(email).child(databaseChallengesPathString)
            .child(nameOfHabit).get().addOnSuccessListener {
                val reqHabit = it.getValue<HabitData>()
                insertHabit(
                    reqHabit!!.name!!,
                    reqHabit!!.days!!,
                    reqHabit!!.pushNotification!!,
                    reqHabit!!.time!!,
                    reqHabit!!.challenger!!
                )
                database.getReference(databaseUsersPath).child(email).child(databaseChallengesPathString)
                    .child(reqHabit.name!!).removeValue()
            }
    }

}