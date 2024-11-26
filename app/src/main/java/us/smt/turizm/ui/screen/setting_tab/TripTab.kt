package us.smt.turizm.ui.screen.setting_tab

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.hilt.getViewModel
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
import us.smt.turizm.domen.model.PlaceDetails

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
        val viewModel = getViewModel<TripViewModel>()
        val state by viewModel.state.collectAsState()
        TripTabScreen(
            state = state,
            onAction = viewModel::onAction
        )
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TripTabScreen(
    state: TripState,
    onAction: (TripIntent) -> Unit
) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F4F6))
        ) {
            GoogleMapScreenWithMyLocation(
                state = state,
                onAction = onAction
            )
        }
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
fun GoogleMapScreenWithMyLocation(
    state: TripState,
    onAction: (TripIntent) -> Unit
) {
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

    if (userLocation != null) {
        LaunchedEffect(
            key1 = Unit
        ) {
            onAction(TripIntent.Init)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(myLocationButtonEnabled = true),
            properties = MapProperties(isMyLocationEnabled = true)
        ) {
            if (!state.isLoading) {
                Markers(list = state.tripList, onAction = onAction)
            }
        }
    }
}

@Composable
fun Markers(list: List<PlaceDetails>, onAction: (TripIntent) -> Unit) {
    list.forEach { placeDetails ->
        Marker(
            state = MarkerState(
                position = LatLng(
                    placeDetails.lat ?: 0.0,
                    placeDetails.long ?: 0.0
                )
            ),
            title = placeDetails.name,
            snippet = placeDetails.service,
            onInfoWindowClick = {
                onAction(TripIntent.OpenItem(placeDetails.id))
            }
        )
    }
}

@SuppressLint("MissingPermission")
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


