package us.smt.turizm.ui.screen.setting_tab

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

object TripTab : Tab {
    private fun readResolve(): Any = TripTab
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2u,
            title = "Trip",
            icon = rememberVectorPainter(Icons.Default.Place)
        )

    @Composable
    override fun Content() {
        TripTabScreen()
    }
}

@Composable
private fun TripTabScreen() {
    Scaffold(
        floatingActionButton = {
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFF2F4F6))
            ) {
                GoogleMapWithLocationPermission()
            }
        }
    )
}


val ancientPlaces = listOf(
    LatLngData(
        title = "Samarkand – Registan Square",
        description = "The most famous historical complex in Central Asia.",
        lngLatLng = LatLng(39.6542, 66.9741)
    ),
    LatLngData(
        title = "Bukhara – Ark Fortress",
        description = "The ancient fortress and residence of Bukhara's rulers.",
        lngLatLng = LatLng(39.7750, 64.4150)
    ),
    LatLngData(
        title = "Khiva – Itchan Kala",
        description = "The walled inner town of the ancient city, a UNESCO World Heritage site.",
        lngLatLng = LatLng(41.3787, 60.3625)
    ),
    LatLngData(
        title = "Shakhrisabz – Amir Timur’s Mausoleum (Ak-Saray)",
        description = "A historical palace built in honor of Amir Timur.",
        lngLatLng = LatLng(39.0561, 66.8322)
    ),
    LatLngData(
        title = "Tashkent – Hazrati Imam Complex",
        description = "A complex housing ancient manuscripts and historical landmarks.",
        lngLatLng = LatLng(41.3221, 69.2363)
    ),
    LatLngData(
        title = "Termez – Fayaztepa",
        description = "Remains of an ancient Buddhist monastery.",
        lngLatLng = LatLng(37.2371, 67.2723)
    ),
    LatLngData(
        title = "Karshi – Odina Madrasa",
        description = "An ancient madrasa that served as a center for learning.",
        lngLatLng = LatLng(38.8665, 65.7888)
    ),
    LatLngData(
        title = "Nurata – Chashma Complex",
        description = "A sacred spring and historical mosque located in the mountains.",
        lngLatLng = LatLng(40.5617, 65.6852)
    ),
    LatLngData(
        title = "Baysun – White Rock Caves (Hissar Mountains)",
        description = "Natural caves and archaeological sites in a historic region.",
        lngLatLng = LatLng(38.2086, 67.1671)
    ),
    LatLngData(
        title = "Kokand – Khudoyar Khan Palace",
        description = "A historical palace built during the Kokand Khanate period.",
        lngLatLng = LatLng(40.5286, 70.9410)
    )
)

data class LatLngData(
    val title: String,
    val description: String,
    val lngLatLng: LatLng
)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GoogleMapWithLocationPermission() {
    var hasPermission by remember { mutableStateOf(false) }
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    // This will launch permission request if needed
    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            hasPermission = true
        }
    }

    // Request location permission
    LocationPermissionScreen(onPermissionGranted = {
        hasPermission = true
    })

    // Once permission is granted, show the map
    if (hasPermission) {
        GoogleMapScreenWithMyLocation()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionScreen(onPermissionGranted: () -> Unit) {
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            onPermissionGranted()
        }
    }

    when {
        locationPermissionState.allPermissionsGranted -> {}

        locationPermissionState.shouldShowRationale -> {
            // Explain why the app needs this permission
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Location access is required to display your current position.")
                Button(onClick = { locationPermissionState.launchMultiplePermissionRequest() }) {
                    Text("Grant Permission")
                }
            }
        }

        else -> {
            LaunchedEffect(Unit) {
                locationPermissionState.launchMultiplePermissionRequest()
            }
        }
    }
}

@Composable
fun GoogleMapScreenWithMyLocation() {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val cameraPositionState = rememberCameraPositionState()

    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(Unit) {
        getCurrentLocation(fusedLocationClient) { latLng ->
            userLocation = latLng
            userLocation?.let {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 6f)
            }
        }
    }

    // Ensure that location data is available before showing the map
    if (userLocation != null) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(myLocationButtonEnabled = true),
            properties = MapProperties(isMyLocationEnabled = true)
        ) {
            ancientPlaces.forEach {
                Marker(
                    state = MarkerState(position = it.lngLatLng),
                    title = it.title,
                    snippet = it.description
                )
            }
        }
    }
}

@SuppressLint("MissingPermission") // Ensure location permission is handled
fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    send: (LatLng) -> Unit
) {
    try {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            send(LatLng(it.latitude, it.longitude))
        }
    } catch (e: Exception) {
        send(LatLng(41.0, 69.0))
    }
}


