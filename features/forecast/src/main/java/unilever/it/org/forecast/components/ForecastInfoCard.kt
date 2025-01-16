package unilever.it.org.forecast.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import unilever.it.org.domain.models.Forecast

@Composable
fun ForecastInfoCard(forecast: Forecast) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(forecast.date, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Text(
                    "${forecast.temperature}°C",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    forecast.status,
                )
            }

            AsyncImage(
                model = forecast.iconUrl,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(50.dp)
                    .background(color = MaterialTheme.colorScheme.secondary),
            )
        }
    }
}