package com.example.baatcheet.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.baatcheet.R
import com.example.baatcheet.ui.chat.ChatListActivity
import com.example.baatcheet.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {

            startActivity(Intent(this, ChatListActivity::class.java))
        } else {

            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish()

    }
}