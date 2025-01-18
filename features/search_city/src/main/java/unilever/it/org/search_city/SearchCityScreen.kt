package unilever.it.org.search_city

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import unilever.it.org.common_ui.components.CurrentWeatherCard
import unilever.it.org.common_ui.components.ForecastCard
import unilever.it.org.common_ui.components.LocationDetailsCard
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

    Box {
        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        if (!loading && currentWeather == null && forecast.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val preloaderLottieComposition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(
                        unilever.it.org.search_city.R.raw.search_lottie
                    )
                )

                val preloaderProgress by animateLottieCompositionAsState(
                    preloaderLottieComposition,
                    iterations = 1,
                    isPlaying = true
                )

                LottieAnimation(
                    composition = preloaderLottieComposition,
                    progress = preloaderProgress,
                )

            }
        }
        LazyVerticalGrid (
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(64.dp),
            columns = GridCells.Fixed(2),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextField(
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
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
                        shape = RoundedCornerShape(32.dp),
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

            item(span = { GridItemSpan(maxLineSpan) }) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(recentSearch) {
                        Card(modifier = Modifier
                            .clickable {
                                vm.updateSearchValue(it)
                                vm.getWeatherDataByCityName()
                            }.clip(shape = RoundedCornerShape(32.dp))

                        ) {
                            Text(
                                text = it,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                HorizontalDivider()
            }

            currentWeather?.let {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    LocationDetailsCard(currentWeather = it)
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    CurrentWeatherCard(it)
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    HorizontalDivider()
                }

                item {
                    WeatherInfoCard(
                        title = "Humidity",
                        icon = unilever.it.org.common_ui.R.drawable.humidity,
                        value = "${it.humidity}%"
                    )
                }

                item {
                    WeatherInfoCard(
                        title = "Pressure",
                        icon = unilever.it.org.common_ui.R.drawable.pressure,
                        value = it.pressure.toString()
                    )
                }

                item {
                    WeatherInfoCard(
                        title = "Min Temp",
                        icon = unilever.it.org.common_ui.R.drawable.min_temp,
                        value = "${it.minTemp}°"
                    )
                }

                item {
                    WeatherInfoCard(
                        title = "Max Temp",
                        icon = unilever.it.org.common_ui.R.drawable.max_temp,
                        value = "${it.maxTemp}°"
                    )
                }

                item (span = { GridItemSpan(maxLineSpan) } ){
                    ForecastCard(forecast)
                }
            }
        }
    }
}
