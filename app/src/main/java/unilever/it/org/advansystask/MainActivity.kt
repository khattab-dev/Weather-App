package unilever.it.org.advansystask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import unilever.it.org.advansystask.components.CustomBottomAppBar
import unilever.it.org.advansystask.ui.theme.AppTheme
import unilever.it.org.forecast.ForecastScreenRoot
import unilever.it.org.home.HomeScreen
import unilever.it.org.navigation.Routes
import unilever.it.org.search_city.SearchCityScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by remember { mutableStateOf(false) }

            AppTheme (darkTheme = darkTheme) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        CustomBottomAppBar(currentDestination, navController)
                    },
                    floatingActionButton = {
                        FilledIconButton(
                            onClick = {
                                darkTheme = !darkTheme
                            }
                        ) {
                            Icon(
                                modifier = Modifier.padding(8.dp),
                                painter = painterResource(
                                    if (darkTheme) R.drawable.dark_mode
                                    else R.drawable.light_mode
                                ),
                                contentDescription = null,
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Home,
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    ) {
                        composable<Routes.Home> {
                            HomeScreen()
                        }
                        composable<Routes.Forecast> {
                            ForecastScreenRoot()
                        }
                        composable<Routes.Search> {
                            SearchCityScreen()
                        }
                    }
                }
            }
        }
    }
}