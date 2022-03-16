package com.example.sestudentjuhabitapp

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class HabitData(
    var name: String? = null,
    var days: HashMap<String, Boolean>? = null,
    var pushNotification: Boolean? = null,
    var time: String? = null,
    var challenger: String? = null
)



