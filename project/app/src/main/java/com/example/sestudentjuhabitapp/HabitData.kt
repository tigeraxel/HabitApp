package com.example.sestudentjuhabitapp

import com.google.firebase.database.IgnoreExtraProperties

const val databaseUsersPath = "users"
const val databaseHabitsPathString = "Habits"
const val databaseUserID = "userID"
const val databaseChallengesPathString = "HabitChallenges"
const val invalidHabitErrorMessage = "Habit doesn't exist"


@IgnoreExtraProperties
data class HabitData(
    var name: String? = null,
    var days: HashMap<String, Boolean>? = null,
    var pushNotification: Boolean? = null,
    var time: String? = null,
    var challenger: String? = null
)



