package unilever.it.org.forecast

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun ForecastScreenRoot(
    navHostController: NavHostController,
    vm: ForecastViewModel = hiltViewModel<ForecastViewModel>()
) {
    ForecastScreen()
}