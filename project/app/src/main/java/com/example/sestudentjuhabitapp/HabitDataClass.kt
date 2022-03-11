package com.example.sestudentjuhabitapp

var day = HashMap<String,Boolean>()

data class HabitDataClass(
    val name : String,
    val days : HashMap<String , Boolean>,
    val pushNotification : Boolean,
    val time : String
){


    constructor() : this("",day,true,"")
    }


