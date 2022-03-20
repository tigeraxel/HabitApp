package com.example.sestudentjuhabitapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    val habit = HabitClass()
    private var mAuth = FirebaseAuth.getInstance();
    private val database = Firebase.database
    private var email = mAuth.currentUser?.email.toString().replace(".", "")
    lateinit var habitsRef: DatabaseReference
    lateinit var habitList: MutableList<HabitData>
    lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)

        val btnCreateHabitActivity = findViewById<Button>(R.id.btnCreateHabitActivity)
        val btnChallengeActivity = findViewById<Button>(R.id.btnChallengeActivity)
        val btnCalenderActivity = findViewById<Button>(R.id.btnCalendarActivity)
        val btnSettingsActivity = findViewById<ImageButton>(R.id.btnSettingsActivity)

        //Starts NewHabitActivity when clicked
        btnCreateHabitActivity.setOnClickListener {
            val intent = Intent(this, NewHabitActivity::class.java)
            startActivity(intent)
        }

        //Starts ChallengeActivity when clicked
        btnChallengeActivity.setOnClickListener {
            val intent = Intent(this, ChallengeActivity::class.java)
            startActivity(intent)
        }

        //Starts CalendarActivity when clicked
        btnCalenderActivity.setOnClickListener {
            val intent = Intent(this, CalenderActivity::class.java)
            startActivity(intent)
        }

        //Starts SettingsActivity when clicked
        btnSettingsActivity.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        habitList = mutableListOf()
        habitsRef = database.getReference("users").child(email).child("Habits")

        database.getReference("users").child(email).child("HabitChallenges").get()
            .addOnSuccessListener {
                if (it.hasChildren()) {
                    for (snapshot in it.children) {
                        var reqHabit = snapshot.getValue<HabitData>()
                        Log.i("habit", reqHabit.toString())
                        val dialogBuilder = AlertDialog.Builder(this)
                        var arrayOfDays = arrayListOf<String>()
                        if (reqHabit!!.days!!["monday"] == true)
                            arrayOfDays.add("monday")
                        if (reqHabit!!.days!!["tuesday"] == true)
                            arrayOfDays.add("tuesday")
                        if (reqHabit!!.days!!["wednesday"] == true)
                            arrayOfDays.add("wednesday")
                        if (reqHabit!!.days!!["thursday"] == true)
                            arrayOfDays.add("thursday")
                        if (reqHabit!!.days!!["friday"] == true)
                            arrayOfDays.add("friday")
                        if (reqHabit!!.days!!["saturday"] == true)
                            arrayOfDays.add("saturday")
                        if (reqHabit!!.days!!["sunday"] == true)
                            arrayOfDays.add("sunday")
                        var Days = arrayOfDays.toString().replace("[", "")
                        Days = Days.replace("]", "")
                        var Notification = ""
                        if (reqHabit.pushNotification == true)
                            Notification = "Notifications is on"
                        else Notification = "Notifications is off"



                        dialogBuilder.setMessage(
                            " ${reqHabit!!.challenger} have challenged you!" + "\n" +
                                    "The name of the challenge is ${reqHabit!!.name}" + "\n" +
                                    "The challenge is done on $Days" + "\n" +
                                    "The time everday is ${reqHabit!!.time}" + "\n" +
                                    " $Notification"
                        )
                            // if the dialog is cancelable
                            .setCancelable(false)
                            // positive button text and action
                            .setPositiveButton(
                                "accept",
                                DialogInterface.OnClickListener { dialog, id ->
                                    habit.acceptChallenge(reqHabit.name!!)
                                    dialog.cancel()
                                })
                            // negative button text and action
                            .setNegativeButton(
                                "deny",
                                DialogInterface.OnClickListener { dialog, id ->
                                    habit.deleteChallenge(reqHabit.name!!)
                                    dialog.cancel()
                                })

                        // create dialog box
                        val alert = dialogBuilder.create()
                        // set title for alert dialog box
                        alert.setTitle("Challenge Request")
                        // show alert dialog
                        alert.show()
                    }
                }
            }


        habitsRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (h in snapshot.children) {
                        val habit = h.getValue(HabitData::class.java)
                        habitList.add(habit!!)
                    }
                    val adapter = habitAdapter(applicationContext, R.layout.habits, habitList)
                    listView.adapter = adapter
                    listView.setOnItemClickListener { parent, view, position, id ->

                        var selectedHabit = habitList[position]

                        // Alert dialog that allows the user to select if they want to edit or view the habit in a calendar view
                        val selectActionDialog = AlertDialog.Builder(this@MainActivity)
                        selectActionDialog.setMessage(R.string.selected_habit_popup_text)
                        selectActionDialog.setCancelable(true)
                        selectActionDialog.setPositiveButton(R.string.select_edit_habit,
                            DialogInterface.OnClickListener { _, _ ->
                                val changeHabitIntent = Intent(this@MainActivity,ChangeHabitActivity::class.java)
                                changeHabitIntent.putExtra("habitName", selectedHabit.name!!)
                                startActivity(changeHabitIntent)
                            })

                        selectActionDialog.setNegativeButton(R.string.select_show_in_calendar,
                            DialogInterface.OnClickListener { _, _ ->
                            val habitCalendarIntent = Intent(this@MainActivity,CalenderActivity::class.java)
                            habitCalendarIntent.putExtra("habitName", selectedHabit.name!!)
                            startActivity(habitCalendarIntent)
                        })

                        val alert = selectActionDialog.create()
                        alert.show()

                        //changeHabitIntent(selectedHabit.name!!)

                        // The item that was clicked
                        // val intent = Intent(this, BookDetailActivity::class.java)
                        // startActivity(intent)
                    }
                }
            }

        })
    }

    fun changeHabitIntent(habitName: String) {
        var intent = Intent(this, ChangeHabitActivity::class.java)
        intent.putExtra("habitName", habitName)
        startActivity(intent)
    }
}
