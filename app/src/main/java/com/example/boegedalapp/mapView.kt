package com.example.boegedalapp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.MapView
import android.os.Bundle
import androidx.compose.foundation.layout.Box

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.boegedalapp.databinding.ActivityMapsBinding
@Composable
fun GoogleMapView(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            MapView(context).apply {
                // Initialize the MapView
                onCreate(null)
            }
        },
        update = { mapView ->
            mapView.getMapAsync { googleMap ->
                // Do operations on the GoogleMap
                // For example, adding markers or moving the camera
                googleMap.apply {
                    // Add a marker
                    val sydney = LatLng(-34.0, 151.0)
                    addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))

                    // Move camera to the marker
                    moveCamera(CameraUpdateFactory.newLatLng(sydney))
                }
            }
        }
    )
}


@Composable
fun PreviewMap() {
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMapView(modifier = Modifier.fillMaxSize())
    }
}
