package com.example.sestudentjuhabitapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class createAccountActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        mAuth = FirebaseAuth.getInstance();

        val btnCreate = findViewById<Button>(R.id.btnCreate)
        val btnLoginToAccount = findViewById<Button>(R.id.btnLoginToAccount)
        val createEmail = findViewById<EditText>(R.id.createEmailEditText)
        val createPassword = findViewById<EditText>(R.id.createPasswordEditText)

        btnLoginToAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        btnCreate.setOnClickListener {
            mAuth!!.createUserWithEmailAndPassword(
                createEmail.getText().toString(),
                createPassword.getText().toString()
            )
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val userID = mAuth!!.currentUser!!.providerId
                        startActivity(
                            Intent(this, MainActivity::class.java).putExtra(
                                "userID",
                                userID
                            )
                        )
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this@createAccountActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
        }
    }
}