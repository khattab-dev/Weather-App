package unilever.it.org.forecast

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ForecastScreen(
    state: ForecastState,
    onAction: (ForecastActions) -> Unit,
) {
    val activity = LocalContext.current as ComponentActivity
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
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(state.forecasts, key = { it.date }) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(verticalArrangement = Arrangement.Center) {
                            Text(it.date, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                            Text(
                                "${it.temperature}°C",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                it.status,
                            )
                        }

                        AsyncImage(
                            model = it.iconUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .size(50.dp)
                                .background(color = MaterialTheme.colorScheme.secondary),
                        )
                    }
                }
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
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(
                activity,
                "Error fetching location: ${exception.localizedMessage}",
                Toast.LENGTH_SHORT
            ).show()
        }
}