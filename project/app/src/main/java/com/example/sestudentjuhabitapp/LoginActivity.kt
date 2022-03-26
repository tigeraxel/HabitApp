package com.example.sestudentjuhabitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.Toast
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

const val authenticationFailure = "Authentication failed."
const val googleSignInSuccess = "Google Sign in Success"
const val googleSignInFailure = "Google Sign in Failed, try again later"

class LoginActivity : AppCompatActivity() {
    private val validation = ValidationClass()
    private val maxEmailLength = validation.maxEmailLength
    private val minEmailLength = validation.minEmailLength
    private val minPasswordLength = validation.minPasswordLength
    private val maxPasswordLength = validation.maxPasswordLength

    private var mAuth: FirebaseAuth? = null
    private lateinit var googleSignInClient: GoogleSignInClient


    lateinit var loginEmail: EditText
    lateinit var loginPassword: EditText

    companion object {
        private const val RC_SIGN_IN = 420;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance();

        val btnLogin = findViewById<Button>(R.id.btnCreate)
        val btnCreateAccount = findViewById<Button>(R.id.createAccountButton)
        loginEmail = findViewById(R.id.loginEmailEditText)
        loginPassword = findViewById(R.id.loginPasswordEditText)
        val btnGoogleSignIn =
            findViewById<com.google.android.gms.common.SignInButton>(R.id.btnGoogleSignIn)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Todo: What is this?
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        btnGoogleSignIn.setOnClickListener {
            signIn()
        }

        btnCreateAccount.setOnClickListener {
            startActivity(Intent(this, createAccountActivity::class.java))
        }
        btnLogin.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            var errors = returnValidationErrors()
            if (errors.isEmpty()) {
                mAuth!!.signInWithEmailAndPassword(
                    loginEmail.getText().toString(),
                    loginPassword.getText().toString()
                )
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            progressBar.visibility = View.INVISIBLE

                            val userID = mAuth!!.currentUser!!.providerId
                            startActivity(
                                Intent(this, MainActivity::class.java).putExtra(
                                    databaseUserID,
                                    userID
                                )

                            )
                            finish()
                        } else {
                            progressBar.visibility = View.INVISIBLE
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                this@LoginActivity, authenticationFailure,
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUI(null)
                        }
                    }
            } else {
                validation.showValidationErrors(errors, this)
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, googleSignInSuccess, Toast.LENGTH_SHORT).show()
                    val userID = mAuth!!.currentUser!!.providerId
                    startActivity(
                        Intent(this, MainActivity::class.java).putExtra(
                            databaseUserID,
                            userID
                        )
                    )
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this,
                        googleSignInFailure,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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


    fun returnValidationErrors(): ArrayList<String> {
        var errorsArray = ArrayList<String>()
        if (loginEmail.text.length > maxEmailLength)
            errorsArray.add(
                getString(R.string.the_email_should_be_max) + " " + maxEmailLength.toString() + " " + getString(
                    R.string.characters
                ) + "\n"
            )
        if (loginEmail.text.length < minEmailLength)
            errorsArray.add(
                getString(R.string.the_email_should_be_min) + " " + minEmailLength.toString() + " " + getString(
                    R.string.characters
                ) + "\n"
            )

        if (loginPassword.text.length > maxPasswordLength)
            errorsArray.add(
                getString(R.string.the_password_should_be_max) + " " + maxPasswordLength.toString() + " " + getString(
                    R.string.characters
                ) + "\n"
            )

        if (loginPassword.text.length < minPasswordLength)
            errorsArray.add(
                getString(R.string.the_password_should_be_min) + " " + minPasswordLength.toString() + " " + getString(
                    R.string.characters
                ) + "\n"
            )

        return errorsArray
    }
}