package unilever.it.org.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem<T : Any>(
    val title: String,
    val iconImageVector: ImageVector? = null,
    val iconDrawableRes: Int? = null,
    val route: T
) {
    companion object {
        val items = listOf(
            NavBarItem(
                title = "Home",
                iconDrawableRes = R.drawable.weather,
                route = Routes.Home,
            ),
            NavBarItem(
                title = "Search",
                iconImageVector = Icons.Default.Search,
                route = Routes.Search,
            ),
            NavBarItem(
                title = "Forecast",
                iconDrawableRes = R.drawable.forecast,
                route = Routes.Forecast,
            ),
        )
    }
}
