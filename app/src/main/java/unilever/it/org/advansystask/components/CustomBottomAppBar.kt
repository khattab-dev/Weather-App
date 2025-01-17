package unilever.it.org.advansystask.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import unilever.it.org.navigation.NavBarItem

@Composable
fun CustomBottomAppBar(
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigation(
        Modifier.clip(RoundedCornerShape(8.dp)).navigationBarsPadding(),
    ) {
        NavBarItem.items.forEach { item ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    if (item.iconImageVector != null) {
                        Icon(
                            imageVector = item.iconImageVector!!,
                            contentDescription = null,
                        )
                    } else if (item.iconDrawableRes != null) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = item.iconDrawableRes!!),
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    }
}