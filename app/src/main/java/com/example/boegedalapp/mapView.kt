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
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.GoogleMap

data class Place(val name: String, val lat: Double, val Lng: Double)
val places = listOf(Place("Noma", 55.6828, 12.6106),
                    Place("Hærværk", 56.1477, 10.1960),
                    Place("Alchemist", 55.6940, 11.2000))

@Composable
fun MapScreen() {
    val boegedal = LatLng(55.6720, 9.4073)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(boegedal, 11f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = boegedal),
            title = "Boegedal Brew"
        )

        for (place in places){
            val pos = LatLng(place.lat, place.Lng)
            Marker(
                state = MarkerState(position = pos),
                title = place.name
            )
        }
    }
}



@Composable
fun PreviewMap() {
    Box(modifier = Modifier.fillMaxSize()) {
        MapScreen()
    }
}