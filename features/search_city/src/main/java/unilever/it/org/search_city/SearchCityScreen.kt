package unilever.it.org.search_city

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import unilever.it.org.common_ui.R
import unilever.it.org.common_ui.components.CurrentWeatherCard
import unilever.it.org.common_ui.components.ForecastInfoCard
import unilever.it.org.common_ui.components.WeatherHorizontalInfoCard
import unilever.it.org.common_ui.components.WeatherInfoCard
import unilever.it.org.domain.models.NetworkError

@Composable
fun SearchCityScreen(
    vm: SearchCityViewModel = hiltViewModel<SearchCityViewModel>()
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val searchValue by vm.searchValue.collectAsStateWithLifecycle()
    val currentWeather by vm.currentWeather.collectAsStateWithLifecycle()
    val forecast by vm.forecast.collectAsStateWithLifecycle()
    val loading by vm.loading.collectAsStateWithLifecycle()
    var isEmptySearch by remember { mutableStateOf(false) }
    val recentSearch by vm.recentSearch.collectAsStateWithLifecycle()

    LaunchedEffect(lifeCycleOwner.lifecycle) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                vm.error.collect { error ->
                    if (error == NetworkError.NOT_FOUND) {
                        Toast.makeText(
                            context,
                            context.getString(unilever.it.org.search_city.R.string.city_not_found),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(unilever.it.org.search_city.R.string.something_went_wrong_please_try_again_later),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    if (loading) {
        CircularProgressIndicator()
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    value = searchValue ?: "",
                    onValueChange = {
                        isEmptySearch = false
                        vm.updateSearchValue(it)
                    },
                    placeholder = {
                        Text(text = "Enter city name")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            vm.getWeatherDataByCityName()
                        }
                    ),

                    isError = isEmptySearch, singleLine = true
                )

                IconButton(onClick = {
                    if (searchValue.isNullOrEmpty()) {
                        isEmptySearch = true
                        return@IconButton
                    }
                    vm.getWeatherDataByCityName()
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(recentSearch) {
                    Card(modifier = Modifier.clickable {
                        vm.updateSearchValue(it)
                        vm.getWeatherDataByCityName()
                    }) {
                        Text(text = it, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }


        if (currentWeather != null) {
            currentWeather?.let {
                item {
                    CurrentWeatherCard(currentWeather!!)
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
                            icon = R.drawable.feels_temp,
                            value = it.feelsLike.toString()
                        )
                        WeatherInfoCard(
                            title = "Max Temp",
                            icon = R.drawable.max_temp,
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



        if (forecast.isNotEmpty()) {
            itemsIndexed(forecast, key = { index, forecast -> forecast.date }) { i, item ->
                ForecastInfoCard(forecast = item)
            }
        }
    }
}