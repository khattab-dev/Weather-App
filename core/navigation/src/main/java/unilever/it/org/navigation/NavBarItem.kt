package unilever.it.org.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem<T : Any>(
    val title: String,
    val icon: ImageVector,
    val route: T
) {
    companion object {
        val items = listOf(
            NavBarItem(
                title = "Home",
                icon = Icons.Default.Home,
                route = Routes.Home,
            ),
            NavBarItem(
                title = "Search",
                icon = Icons.Default.Search,
                route = Routes.Search,
            ),
            NavBarItem(
                title = "Forecast",
                icon = Icons.Default.FavoriteBorder,
                route = Routes.Forecast,
            ),
        )
    }
}
