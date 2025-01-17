package unilever.it.org.forecast

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ForecastScreenRoot(
    vm: ForecastViewModel = hiltViewModel<ForecastViewModel>()
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(lifeCycleOwner.lifecycle) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                vm.events.collect { event ->
                    when (event) {
                        is ForecastEvents.Error -> {
                            Toast.makeText(
                                context,
                                event.error.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }


    ForecastScreen(
        state = state,
        onAction = vm::onAction
    )
}

