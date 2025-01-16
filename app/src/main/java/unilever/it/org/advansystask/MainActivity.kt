package unilever.it.org.advansystask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import unilever.it.org.advansystask.components.CustomBottomAppBar
import unilever.it.org.advansystask.ui.theme.AdvansysTaskTheme
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
            AdvansysTaskTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        CustomBottomAppBar(currentDestination, navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Home,
                        modifier = Modifier.padding(innerPadding).padding(16.dp)
                    ) {
                        composable<Routes.Home> {
                            HomeScreen(
                                navHostController = navController,
                            )
                        }
                        composable<Routes.Forecast> {
                            ForecastScreenRoot(
                                navHostController = navController,
                            )
                        }
                        composable<Routes.Search> {
                            SearchCityScreen(
                                navHostController = navController,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdvansysTaskTheme {
        Greeting("Android")
    }
}