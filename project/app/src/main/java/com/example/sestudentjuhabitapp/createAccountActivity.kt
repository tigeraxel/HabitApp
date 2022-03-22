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
    val validation = ValidationClass()
    val maxEmailLength = validation.maxEmailLength
    val minEmailLength = validation.minEmailLength
    val minPasswordLength = validation.minPasswordLength
    val maxPasswordLength = validation.maxPasswordLength

    private var mAuth: FirebaseAuth? = null

    lateinit var createEmail: EditText
    lateinit var createPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        mAuth = FirebaseAuth.getInstance();

        val btnCreate = findViewById<Button>(R.id.btnCreate)
        val btnLoginToAccount = findViewById<Button>(R.id.btnLoginToAccount)
        createEmail = findViewById(R.id.createEmailEditText)
        createPassword = findViewById(R.id.createPasswordEditText)

        btnLoginToAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        btnCreate.setOnClickListener {
            var errors: ArrayList<String> = returnValidationErrors()
            if (errors.isEmpty()) {
                mAuth!!.createUserWithEmailAndPassword(
                    createEmail.getText().toString(),
                    createPassword.getText().toString()
                ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val userID = mAuth!!.currentUser!!.providerId
                        startActivity(
                            Intent(this, MainActivity::class.java).putExtra(
                                "userID",
                                userID
                            )
                        )
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this@createAccountActivity,
                            "Google Authentication denied the email adress.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                validation.showValidationErrors(errors, this)
            }
        }
    }

    fun returnValidationErrors(): ArrayList<String> {
        var errorsArray = ArrayList<String>()
        if (createEmail.text.length > maxEmailLength)
            errorsArray.add(
                getString(R.string.the_email_should_be_max) + " " + maxEmailLength.toString() + " " + getString(
                    R.string.characters
                ) + "\n"
            )
        if (createEmail.text.length < minEmailLength)
            errorsArray.add(
                getString(R.string.the_email_should_be_min) + " " + minEmailLength.toString() + " " + getString(
                    R.string.characters
                ) + "\n"
            )

        if (createPassword.text.length > maxPasswordLength)
            errorsArray.add(
                getString(R.string.the_password_should_be_max) + " " + maxPasswordLength.toString() + " " + getString(
                    R.string.characters
                ) + "\n"
            )

        if (createPassword.text.length < minPasswordLength)
            errorsArray.add(
                getString(R.string.the_password_should_be_min) + " " + minPasswordLength.toString() + " " + getString(
                    R.string.characters
                ) + "\n"
            )

        return errorsArray
    }
}