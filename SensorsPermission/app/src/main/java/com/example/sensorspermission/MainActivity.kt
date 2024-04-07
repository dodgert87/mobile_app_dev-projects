package com.example.sensorspermission

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensorspermission.ui.theme.SensorsPermissionTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensorsPermissionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LocationDisplay()
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationDisplay() {
    val context = LocalContext.current
    val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
    var locationText by remember { (mutableStateOf("Waiting for location...")) }

    val locationPermission =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val coarseLocationPermission =
        rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)



    if (locationPermission.status.isGranted) {
        Column {


            Text(
                text = "Location permission Granted",
                modifier = Modifier.padding(16.dp),
                style = TextStyle(fontSize = 20.sp)
            )
            Text(
                locationText,
                modifier = Modifier.padding(16.dp),
                style = TextStyle(fontSize = 20.sp)
            )

            Button(onClick = {
                val location = locationManager.getLastKnownLocation(LocationManager.FUSED_PROVIDER)
                if (location != null) {

                    locationText = "Lat: ${location.latitude}, Lng: ${location.longitude}"
                }

            }, modifier = Modifier.padding(20.dp))
            {
                Text(text = "Get Last Known Location", modifier = Modifier.padding(16.dp))
            }
            Button(onClick = {
                val locationEventListener =
                    LocationListener { location ->
                        locationText = "Lat: ${location.latitude}, Lng: ${location.longitude}"
                    }
                locationManager.requestLocationUpdates(
                    LocationManager.FUSED_PROVIDER,
                    100,
                    0.0f,
                    locationEventListener
                )
            }, modifier = Modifier.padding(20.dp)) {
                Text(text = "Get Live Location", modifier = Modifier.padding(16.dp))

            }
        }

    } else {
        Column {
            Button(onClick = {
                locationPermission.launchPermissionRequest()
                coarseLocationPermission.launchPermissionRequest()

            }) {
                Text(text = "Request location permission")
            }
        }

    }
}
