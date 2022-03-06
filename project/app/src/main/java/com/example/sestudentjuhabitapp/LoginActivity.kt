package com.example.sestudentjuhabitapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.Toast

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.EditText


class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance();


        val btnLogin = findViewById<Button>(R.id.btnCreate)
        val btnCreateAccount = findViewById<Button>(R.id.createAccountButton)
        val loginEmail = findViewById<EditText>(R.id.loginEmailEditText)
        val loginPassword = findViewById<EditText>(R.id.loginPassswordEditText)

        btnCreateAccount.setOnClickListener {
            startActivity(Intent(this, createAccountActivity::class.java))
        }
        btnLogin.setOnClickListener {

            mAuth!!.signInWithEmailAndPassword(
                loginEmail.getText().toString(),
                loginPassword.getText().toString()
            )
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val userID = mAuth!!.currentUser!!.providerId
                        startActivity(
                            Intent(this, MainActivity::class.java).putExtra(
                                "userID",
                                userID
                            )
                        )
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this@LoginActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }

                    // ...
                }
        }

        //val btnLogin = findViewById<Button>(R.id.btnLogin)

        //Login in the user, starts MainActivity when clicked

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.getCurrentUser();
        updateUI(currentUser)


    }

    fun updateUI(user: FirebaseUser?) {

        return
    }
}