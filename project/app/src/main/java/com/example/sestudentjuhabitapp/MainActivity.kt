package com.example.sestudentjuhabitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

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
                }
            }


        })
    }

}
