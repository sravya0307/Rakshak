package com.example.rakshakkaraksha

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class contacts : AppCompatActivity() {

    private lateinit var phone1Input: EditText
    private lateinit var phone2Input: EditText
    private lateinit var phone3Input: EditText
    private lateinit var phone4Input: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        phone1Input = findViewById(R.id.phone_number_input_1)
        phone2Input = findViewById(R.id.phone_number_input_2)
        phone3Input = findViewById(R.id.phone_number_input_3)
        phone4Input = findViewById(R.id.phone_number_input_4)
        saveButton = findViewById(R.id.save_button)
        cancelButton = findViewById(R.id.cancel_button)

        saveButton.setOnClickListener {
            val phone1 = phone1Input.text.toString().trim()
            val phone2 = phone2Input.text.toString().trim()
            val phone3 = phone3Input.text.toString().trim()
            val phone4 = phone4Input.text.toString().trim()

            if (isValidPhoneNumber(phone1) && isValidPhoneNumber(phone2) &&
                isValidPhoneNumber(phone3) && isValidPhoneNumber(phone4)) {
                saveContactsToServer(phone1, phone2, phone3, phone4)
                finish() // assuming you want to close this activity after saving
            } else {
                Toast.makeText(
                    applicationContext,
                    "Invalid phone number format. Please enter valid numbers.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        cancelButton.setOnClickListener { finish() } // assuming you want to close this activity when cancel button is clicked
    }

    private fun saveContactsToServer(phone1: String, phone2: String, phone3: String, phone4: String) {
        val contactsArray = JSONArray()
        contactsArray.put(phone1)
        contactsArray.put(phone2)
        contactsArray.put(phone3)
        contactsArray.put(phone4)

        sendContactsToBackend(contactsArray)
    }

    private fun sendContactsToBackend(contactsArray: JSONArray) {
        val url = "http://localhost:5000/addingLoocationToDb" // Replace with your server URL

        val requestQueue = Volley.newRequestQueue(applicationContext)

        val jsonObject = JSONObject()
        jsonObject.put("contacts", contactsArray)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->
                Toast.makeText(
                    applicationContext,
                    "Contacts sent successfully",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("Server Response", response.toString())
            },
            { error ->
                Toast.makeText(
                    applicationContext,
                    "Error sending contacts: $error",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("Server Error", error.toString())
            })

        requestQueue.add(jsonObjectRequest)
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.isNotEmpty() // You can add more validation logic here if needed
    }
}
