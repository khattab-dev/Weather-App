package unilever.it.org.home

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import unilever.it.org.common_ui.components.CurrentWeatherCard
import unilever.it.org.common_ui.components.WeatherHorizontalInfoCard
import unilever.it.org.common_ui.components.WeatherInfoCard


@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    vm: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val activity = LocalContext.current as ComponentActivity
    val coarseLocationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            fetchLocationAndWeather(activity, vm)
        } else {
            Toast.makeText(activity, "Permission denied. Cannot fetch weather.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    val data = vm.currentWeather.collectAsStateWithLifecycle()
    val loading = vm.loading.collectAsStateWithLifecycle()

    LaunchedEffect(coarseLocationPermissionState.status) {
        when {
            coarseLocationPermissionState.status.isGranted -> {
                fetchLocationAndWeather(activity, vm)
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

    if (loading.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    data.value?.let {
        LazyColumn(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.spacedBy(24.dp)) {
            item {
                CurrentWeatherCard(it)
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    WeatherInfoCard(
                        title = "Min Temp",
                        icon = R.drawable.min_temp,
                        value = it.minTemp.toString()
                    )
                    WeatherInfoCard(
                        title = "Feels Like",
                        icon =  R.drawable.feels_temp,
                        value = it.feelsLike.toString()
                    )
                    WeatherInfoCard(
                        title = "Max Temp",
                        icon =  R.drawable.max_temp,
                        value = it.maxTemp.toString()
                    )
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    WeatherHorizontalInfoCard(
                        title = "Pressure",
                        icon = R.drawable.pressure,
                        value = it.pressure.toString()
                    )
                    WeatherHorizontalInfoCard(
                        title = "Humidity",
                        icon = R.drawable.humidity,
                        value = it.minTemp.toString()
                    )
                    WeatherHorizontalInfoCard(
                        title = "Wind Speed",
                        icon = R.drawable.wind_speed,
                        value = it.minTemp.toString()
                    )

                    WeatherHorizontalInfoCard(
                        title = "Wind Degree",
                        icon = R.drawable.wind_degree,
                        value = it.windDegree.toString()
                    )
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
private fun fetchLocationAndWeather(activity: ComponentActivity, vm: HomeViewModel) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                vm.getCurrentWeather(location.latitude, location.longitude)
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