package com.example.sestudentjuhabitapp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HabitClass {

    private var mAuth = FirebaseAuth.getInstance();
    var userID = mAuth!!.currentUser!!.uid
    val database = Firebase.database
    val userReference = database.getReference("users").child(userID).child("Habits")


    fun insertHabit(
        name: String,
        days: HashMap<String,Boolean>,
        pushNotification: Boolean,
        time: String
    ) {
        var newHabit = HabitDataClass(days, pushNotification, time)
        userReference.child(name).setValue(newHabit)
    }

}