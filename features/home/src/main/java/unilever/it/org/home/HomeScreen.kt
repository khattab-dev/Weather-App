package unilever.it.org.home

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import unilever.it.org.common_ui.components.CurrentWeatherCard
import unilever.it.org.common_ui.components.ForecastCard
import unilever.it.org.common_ui.components.LocationDetailsCard
import unilever.it.org.common_ui.components.WeatherInfoCard



@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val activity = LocalActivity.current as ComponentActivity
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

    val currentWeatherData by vm.currentWeather.collectAsStateWithLifecycle()
    val forecastData by vm.forecast.collectAsStateWithLifecycle()
    val loading by vm.loading.collectAsStateWithLifecycle()

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

    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    currentWeatherData?.let { weather ->
        LazyVerticalGrid(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(64.dp),
            columns = GridCells.Fixed(2),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                LocationDetailsCard(currentWeather = weather)
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                CurrentWeatherCard(weather)
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                HorizontalDivider()
            }

            item {
                WeatherInfoCard(
                    title = "Humidity",
                    icon = R.drawable.humidity,
                    value = "${weather.humidity}%"
                )
            }

            item {
                WeatherInfoCard(
                    title = "Pressure",
                    icon = R.drawable.pressure,
                    value = weather.pressure.toString()
                )
            }

            item {
                WeatherInfoCard(
                    title = "Min Temp",
                    icon = R.drawable.min_temp,
                    value = "${weather.minTemp}°"
                )
            }

            item {
                WeatherInfoCard(
                    title = "Max Temp",
                    icon = R.drawable.max_temp,
                    value = "${weather.maxTemp}°"
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                ForecastCard(forecastData)
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
                vm.getWeatherData(location.latitude, location.longitude)
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