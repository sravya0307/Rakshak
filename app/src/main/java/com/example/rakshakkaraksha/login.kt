package com.example.rakshakkaraksha
import com.google.firebase.auth.PhoneAuthOptions
import org.json.JSONObject
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class login : AppCompatActivity() {

    private lateinit var phone: EditText
    private lateinit var otp: EditText
    private lateinit var generateOtpButton: Button
    private lateinit var verifyOtpButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    private lateinit var sharedPreferences: SharedPreferences

    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }
        override fun onVerificationFailed(e: FirebaseException) {
            showToast("Verification failed: ${e.message}")
            Log.e("VerificationFailed", "Error: ${e.message}")
        }
        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            showToast("OTP sent to your phone")
            this@login.verificationId = verificationId
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)

        phone = findViewById(R.id.phone)
        otp = findViewById(R.id.otp)
        generateOtpButton = findViewById(R.id.generateOtpButton)
        verifyOtpButton = findViewById(R.id.verifyOtpButton)

        auth = FirebaseAuth.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            startActivity(Intent(this, sos_page::class.java)) // Launch emergency contact dialog
            finish() // finish the login activity so user can't go back
            return
        }

        generateOtpButton.setOnClickListener {
            val phoneNumber = phone.text.toString().trim()
            if (TextUtils.isEmpty(phoneNumber)) {
                phone.error = "Please enter your phone number"
                return@setOnClickListener
            }
            generateOTP(phoneNumber)
        }

        verifyOtpButton.setOnClickListener {
            val code = otp.text.toString().trim()
            if (TextUtils.isEmpty(code)) {
                otp.error = "Please enter the OTP"
                return@setOnClickListener
            }
            verifyOTP(verificationId, code)
        }
    }

    private fun generateOTP(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber")
            .setTimeout(30L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallbacks) // Added line to set callbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyOTP(verificationId: String, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    if (user != null) {
                        val uid = user.uid

                        // Create a JSONObject to store the UID
                        val jsonObject = JSONObject()
                        jsonObject.put("uid", uid)

                        // Send data to server on a background thread (using Volley)
                        sendJsonObjectToServer(jsonObject)

                        showToast("Logged in successfully")
                        startActivity(Intent(this, contacts::class.java)) // Launch emergency contact dialog
                        finish() // Finish the login activity so the user can't go back
                    }
                } else {
                    showToast("Authentication failed: ${task.exception?.message}")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun sendJsonObjectToServer(jsonObject: JSONObject) {
        val url = "http://localhost:5000/addingLoocationToDb" // Replace with your server URL

        // Initialize Volley RequestQueue
        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, jsonObject,
            { response ->
                // Handle successful response from the server (optional)
                Log.d("Server Response", response.toString())
            },
            { error ->
                // Handle error sending data to the server
                Log.e("Server Error", error.toString())
            })
        requestQueue.add(jsonObjectRequest)
    }




}
