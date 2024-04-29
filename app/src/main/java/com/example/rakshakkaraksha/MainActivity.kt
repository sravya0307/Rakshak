package com.example.rakshakkaraksha

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class MainActivity : AppCompatActivity(),OnMapReadyCallback {

    private lateinit var gmap:GoogleMap
    private lateinit var fusedLocClient:FusedLocationProviderClient
    private lateinit var locationText: TextView
    private lateinit var submitbutton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationText=findViewById(R.id.locationText)
        submitbutton=findViewById(R.id.submitbtn)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        fusedLocClient = LocationServices.getFusedLocationProviderClient(this)

    }
    public fun getLocationAddress(location: Location){
        val geocoder = Geocoder(this, Locale.getDefault())
        val latitude = location.latitude
        val longitude = location.longitude

        val addresses: List<Address>? = try {
            geocoder.getFromLocation(latitude, longitude, 1)
        } catch (e: Exception) {
            // Handle potential exceptions (e.g., network issues)
            Log.e("Geocoder", "Error in geocoding: ${e.message}")
            null
        }

        if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0].getAddressLine(0)
            // Use the address string here
            locationText.text = address
        } else {
            // Handle the case where no address is found
            locationText.text = "Address not found."
        }
        submitbutton.setOnClickListener {
            val intent= Intent(this,FirebaseActivity::class.java)
            intent.putExtra("latitude",location.latitude)
            intent.putExtra("longitude",location.longitude)
            Toast.makeText(this,latitude.toString(),Toast.LENGTH_LONG).show()
            startActivity(intent)
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        gmap=googleMap

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        gmap.isMyLocationEnabled=true
        fusedLocClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val curLatLong = LatLng(location.latitude, location.longitude)
                val marker=gmap.addMarker(MarkerOptions().position(curLatLong).draggable(true).title("Your Current Location"))
                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatLong, 15f))

                getLocationAddress(location)
            }
        }
        val dragListener=MyDragListener(this)
        gmap.setOnMarkerDragListener(dragListener)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== LOCATION_PERMISSION_REQUEST_CODE){
            if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                recreate()
            }
            else{
                Toast.makeText(this,"LOCATION PERMISSION IS REQUIRED",Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object{
        val LOCATION_PERMISSION_REQUEST_CODE=1
    }
}