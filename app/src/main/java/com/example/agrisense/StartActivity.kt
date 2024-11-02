package com.example.agrisense

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class StartActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        auth = Firebase.auth
        val signIn=findViewById<Button>(R.id.signinButton)
        val register=findViewById<Button>(R.id.registerButton)

        signIn.setOnClickListener {
            val intent = Intent(this, SignInPage::class.java)
            startActivity(intent)
            finish()
        }
        register.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser=auth.currentUser
        if (currentUser!=null){
            val intent=Intent(this,DataActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            val intent=Intent(this,Register::class.java)
            startActivity(intent)
            finish()
        }
    }
}