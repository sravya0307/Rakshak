package com.example.rakshakkaraksha

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.core.content.ContextCompat

class FormActivity : AppCompatActivity() {
    private lateinit var checkTerms:CheckBox
    private lateinit var continueBtn:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        checkTerms=findViewById(R.id.check1)
        continueBtn=findViewById(R.id.continueButton)
        checkTerms.setOnCheckedChangeListener { buttonView, isChecked ->
            continueBtn.isEnabled=checkTerms.isChecked
            if(continueBtn.isEnabled){
                continueBtn.visibility= View.VISIBLE
                continueBtn.setOnClickListener{
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
            }
            else{
                continueBtn.visibility=View.INVISIBLE
            }
        }
    }

}