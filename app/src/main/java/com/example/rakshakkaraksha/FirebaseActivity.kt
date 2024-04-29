package com.example.rakshakkaraksha

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import org.json.JSONObject

class FirebaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)
        val intent = intent
        val uid="361hBSWB06JixA3Plpcc"
        val latitude = intent.getDoubleExtra("LATITUDE", 0.0)
        val longitude = intent.getDoubleExtra("LONGITUDE", 0.0)
        val locationData = LocationData(uid,latitude, longitude)
        val gson = Gson()
        val json = gson.toJson(locationData) // Convert location data to JSON string

        val url = "Http://192.168.56.1:5000/addingLocation" // Replace with your server POST endpoint URL

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            JSONObject(mapOf("location" to json)), // Send location data in a nested JSON object
            { response ->
                // Handle successful response
                Toast.makeText(this, "Location data sent successfully!", Toast.LENGTH_LONG).show()
            },
            { error ->
                // Handle error
                Toast.makeText(this, "Error sending location data: $error", Toast.LENGTH_LONG).show()
                Log.e("LocationService", "Error:", error)
            }
        )

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }
}