package com.example.rakshakkaraksha
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class smsActivity : AppCompatActivity() {
    val arr_numbers=arrayOf("9347675134","9441213960","7893818807")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.SEND_SMS),
                SMS_PERMISSION_REQUEST_CODE)
        } else {
            for(i in arr_numbers){
                sendSMS(i, "ALERT!!, your fellow Watchman is in trouble. HELP!")
            }

        }
    }
    private fun sendSMS(phoneNumber: String, message: String) {
        val smsManager: SmsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            SMS_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    for(i in arr_numbers){
                        sendSMS(i, "ALERT!!, your fellow Watchman is in trouble. HELP!")
                    }
                } else {
                    Toast.makeText(this,"Permission Required",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    companion object {
        private const val SMS_PERMISSION_REQUEST_CODE = 12
    }
}