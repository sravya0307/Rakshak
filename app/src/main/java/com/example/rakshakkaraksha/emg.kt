package com.example.rakshakkaraksha

import com.google.firebase.firestore.FirebaseFirestore
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import java.util.concurrent.TimeUnit
import com.google.firebase.FirebaseApp


class emg : AppCompatActivity() {

    private lateinit var nextNav: Button // Use camelCase for variable names

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emg)
        FirebaseApp.initializeApp(this)


        val button3 = findViewById<Button>(R.id.button3)
        nextNav = findViewById(R.id.next_nav)

        button3.setOnClickListener {
            startActivity(Intent(this,contacts::class.java))

        }

        nextNav.setOnClickListener {
            startActivity(Intent(this, sos_page::class.java)) // Use class name directly
        }
    }




}
