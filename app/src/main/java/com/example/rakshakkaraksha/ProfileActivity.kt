package com.example.rakshakkaraksha

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ProfileActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val viewmap=findViewById<Button>(R.id.btnviewmap)
        val updatework=findViewById<Button>(R.id.btnupdatework)
        val emergencycon=findViewById<Button>(R.id.btncontacts)
        viewmap.setOnClickListener {
            val intent= Intent(this,AllUsersActivity::class.java)
            startActivity(intent)
        }
        updatework.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}