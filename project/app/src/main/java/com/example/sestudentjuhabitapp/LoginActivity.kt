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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.internal.Objects
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object{
        private const val RC_SIGN_IN = 420;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance();

        val btnLogin = findViewById<Button>(R.id.btnCreate)
        val btnCreateAccount = findViewById<Button>(R.id.createAccountButton)
        val loginEmail = findViewById<EditText>(R.id.loginEmailEditText)
        val loginPassword = findViewById<EditText>(R.id.loginPasswordEditText)
        val btnGoogleSignIn = findViewById<com.google.android.gms.common.SignInButton>(R.id.btnGoogleSignIn)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
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
                        finish()
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



    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        Log.d("LoginActivityTest", requestCode.toString())
        Log.d("LoginActivityTest", resultCode.toString())
        Log.d("LoginActivityTest", data.toString())

        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("LoginActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("LoginActivity", "Google sign in failed", e)
                }
            }
            else{
                Log.w("LoginActivity", exception.toString() )
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LoginActivity", "signInWithCredential:success")
                    Toast.makeText(this, "Google Sign in Success", Toast.LENGTH_SHORT).show()
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
                    Log.w("LoginActivity", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Google Sign in Failed, try again later", Toast.LENGTH_SHORT).show()
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
}