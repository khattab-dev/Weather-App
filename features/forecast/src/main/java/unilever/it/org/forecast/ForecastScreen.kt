package unilever.it.org.forecast

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import unilever.it.org.common_ui.components.ForecastCard

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ForecastScreen(
    state: ForecastState,
    onAction: (ForecastActions) -> Unit,
) {
    val activity = LocalActivity.current as ComponentActivity
    val coarseLocationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            fetchLocationAndWeather(activity, onAction = onAction)
        } else {
            Toast.makeText(activity, "Permission denied. Cannot fetch weather.", Toast.LENGTH_SHORT)
                .show()

            fetchLocationAndWeather(activity, onAction)
        }
    }

    LaunchedEffect(coarseLocationPermissionState.status) {
        when {
            coarseLocationPermissionState.status.isGranted -> {
                fetchLocationAndWeather(activity, onAction)
            }

            coarseLocationPermissionState.status.shouldShowRationale -> {
                Toast.makeText(
                    activity,
                    "Please allow location access to fetch weather.",
                    Toast.LENGTH_SHORT
                ).show()

                fetchLocationAndWeather(activity, onAction)
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }


    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            item {
                ForecastCard(state.forecasts)
            }
        }
    }
}

@SuppressLint("MissingPermission")
private fun fetchLocationAndWeather(
    activity: ComponentActivity,
    onAction: (ForecastActions) -> Unit
) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                onAction(ForecastActions.GetForecastData(location.latitude, location.longitude))
            } else {
                Toast.makeText(
                    activity,
                    "Unable to fetch location. Try again later.",
                    Toast.LENGTH_SHORT
                ).show()

                onAction(ForecastActions.GetForecastData(null, null))
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(
                activity,
                "Error fetching location: ${exception.localizedMessage}",
                Toast.LENGTH_SHORT
            ).show()

            onAction(ForecastActions.GetForecastData(null, null))
        }
}