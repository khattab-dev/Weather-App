package unilever.it.org.search_city

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun SearchCityScreen(
    navHostController: NavHostController,
    vm: SearchCityViewModel = hiltViewModel<SearchCityViewModel>()
) {
    Text("Search Screen")
}