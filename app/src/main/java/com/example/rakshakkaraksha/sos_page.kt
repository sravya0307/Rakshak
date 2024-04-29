package com.example.rakshakkaraksha

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class sos_page : AppCompatActivity() {

    private val REQUEST_CALL_PHONE_PERMISSION = 101
    private val CALL_DELAY_MILLIS = 2000 // milliseconds (adjustable)
    private val RING_TIMEOUT_MILLIS = 30000 // milliseconds (adjustable) // Time to wait for first call to be answered

    private val number100 = "tel:7993674535" // Emergency number 1
    private val number108 = "tel:9502337357" // Emergency number 2

    private var callInProgress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sos_page)

        val sosButton = findViewById<Button>(R.id.sosButton)

        sosButton.setOnClickListener {
            // Check permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestCallPermission()
                return@setOnClickListener
            }

            initiateEmergencyCall(number100)
        }
    }

    private fun requestCallPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PHONE_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initiateEmergencyCall(number100)
            } else {
                Toast.makeText(this, "Permission denied to make phone calls", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initiateEmergencyCall(number: String) {
        if (callInProgress) {
            // Already calling someone, ignore further attempts
            return
        }
        callInProgress = true
        val callIntent = Intent(Intent.ACTION_CALL, Uri.parse(number))
        startActivity(callIntent) // Start call activity




        val timeoutHandler = Handler(Looper.getMainLooper())
        val timeoutRunnable = Runnable {
            if (callInProgress) {
                // Ending calls directly might require additional libraries (not recommended)
                // Consider alternative approaches like informing user and calling next number
                Toast.makeText(this, "First call unanswered. Calling next number in $RING_TIMEOUT_MILLIS/1000 seconds.", Toast.LENGTH_LONG).show()
                callEmergencyNumber(number108) // Schedule call to next number after timeout
            }
            callInProgress = false
        }
        timeoutHandler.postDelayed(timeoutRunnable, RING_TIMEOUT_MILLIS * 1000L) // Convert to milliseconds
    }

    private fun callEmergencyNumber(number: String) {
        // Call the next emergency number only if the first call ended
        if (!callInProgress) {
            initiateEmergencyCall(number)
        }
    }
}
