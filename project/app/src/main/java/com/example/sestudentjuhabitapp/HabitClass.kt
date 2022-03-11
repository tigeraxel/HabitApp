package com.example.sestudentjuhabitapp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HabitClass {

    private var mAuth = FirebaseAuth.getInstance();
    private var userID = mAuth!!.currentUser!!.uid
    private val database = Firebase.database
    private val userReference = database.getReference("users").child(userID).child("Habits")


    fun insertHabit(
        name: String,
        days: HashMap<String,Boolean>,
        pushNotification: Boolean,
        time: String
    ) {
        var newHabit = HabitDataClass(name,days, pushNotification, time)
        userReference.child(name).setValue(newHabit)
    }
    fun returnHashMap(): HashMap<String, Boolean> {
        var day = HashMap<String,Boolean>()
        day.put("",true)
        return(day)
    }

}