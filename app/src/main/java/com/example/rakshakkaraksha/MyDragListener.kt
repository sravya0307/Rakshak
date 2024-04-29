package com.example.rakshakkaraksha

import android.content.Intent
import android.location.Location
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MyDragListener(val mainActivity: MainActivity): GoogleMap.OnMarkerDragListener {
    private lateinit var submitbutton: Button
    override fun onMarkerDrag(p0: Marker) {

    }

    override fun onMarkerDragEnd(p0: Marker) {
        val newPosition = p0?.position // Get the new position after dragging
        Log.d("Drag Ended", "New Marker Position: ${newPosition?.latitude}, ${newPosition?.longitude}")
        val location= Location("New Location")
        location.latitude=newPosition?.latitude?:0.0
        location.longitude=newPosition?.longitude?:0.0
        mainActivity.getLocationAddress(location)


    }

    override fun onMarkerDragStart(p0: Marker) {

    }
}