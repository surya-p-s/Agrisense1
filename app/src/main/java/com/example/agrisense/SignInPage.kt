package com.example.agrisense

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInPage : AppCompatActivity() {


    private lateinit var auth : FirebaseAuth
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var logIn : Button

    private lateinit var sEmail : String
    private lateinit var sPassword : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_page)


        auth = Firebase.auth
        val registerButton = findViewById<Button>(R.id.registerButton1)
        logIn = findViewById(R.id.signIn)
        email = findViewById(R.id.emailText1)
        password = findViewById(R.id.textPassword1)


        logIn.setOnClickListener {

            sEmail = email.text.toString().trim()
            sPassword = password.text.toString().trim()


            auth.signInWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        Toast.makeText(baseContext, "LogIn Successful", Toast.LENGTH_SHORT)
                            .show()
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Try again OR Register", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
        registerButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
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