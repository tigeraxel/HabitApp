package com.example.sestudentjuhabitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val btnChangeUserInfo = findViewById<Button>(R.id.btnChangeUserInfo)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        //Change User Information with the database when clicked
        btnChangeUserInfo.setOnClickListener {

        }

        //Logout the user, and start LoginActivity when clicked
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            //Change "keep inlogged boolean"
            startActivity(intent)
        }
    }
}