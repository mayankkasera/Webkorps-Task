package com.mayank.webkorps_task.ui.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.mayank.webkorps_task.R

class MapsActivity : AppCompatActivity(), OnMapReadyCallback  {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {

        val start = LatLng(23.5236, 77.8140)
        val  end = LatLng(22.7215, 75.8784)
        // Add polylines to the map.
        // Polylines are useful to show a route or some other connection between points.
       googleMap.addPolyline(
           PolylineOptions()
               .clickable(true)
               .add(start, end)
       )

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.

        googleMap.addMarker(MarkerOptions().position(start).title("Vidisha"))
        googleMap.addMarker(MarkerOptions().position(end).title("TI mall Indore"))

        val boundsIndia = LatLngBounds(end, start)
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.10).toInt()
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(boundsIndia, width, height, padding)
        googleMap.animateCamera(cameraUpdate)

        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-23.684, 133.903), 4f))


    }
}