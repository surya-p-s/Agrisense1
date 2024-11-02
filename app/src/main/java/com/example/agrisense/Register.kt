package com.example.agrisense

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var registerButton : Button

    private lateinit var sEmail : String
    private lateinit var sPassword : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        registerButton = findViewById(R.id.registerButton)
        email = findViewById(R.id.emailText2)
        password = findViewById(R.id.textPassword2)




        // create new user
        registerButton.setOnClickListener {

            sEmail = email.text.toString().trim()
            sPassword = password.text.toString().trim()

            auth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(ContentValues.TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Already Register.", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this, SignInPage::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
        }

        val logIN = findViewById<Button>(R.id.logInButton)

        logIN.setOnClickListener {
            val intent = Intent(this,SignInPage::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun updateUI(user: FirebaseUser?) {
        if(user != null) {
            val intent = Intent(this, DataActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}