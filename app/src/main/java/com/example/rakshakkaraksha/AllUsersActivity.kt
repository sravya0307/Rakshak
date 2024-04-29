package com.example.rakshakkaraksha

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class AllUsersActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var gmap: GoogleMap
    private val locations: List<LocationData> = listOf(
        LocationData("a",51.5074, -0.1278),
        LocationData("b",48.8566, 2.3522),
        LocationData("c",40.7128, -74.0060)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_users)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gmap=googleMap
        val count=0
        for (location in locations){
            val latLng = LatLng(location.latitude, location.longitude)
            val t= "WM$count"
            gmap.addMarker(MarkerOptions().position(latLng).title(t))
        }
        if (locations.isNotEmpty()) {
            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(locations[0].latitude, locations[0].longitude), 10f))
        }
    }

}