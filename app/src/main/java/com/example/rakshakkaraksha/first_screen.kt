package com.example.rakshakkaraksha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class first_screen : AppCompatActivity() {
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)
        button=findViewById(R.id.button)
        button.setOnClickListener{
            startActivity(Intent(this,login::class.java))
        }

    }
}