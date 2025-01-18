package unilever.it.org.common_ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import unilever.it.org.domain.models.CurrentWeather

@Composable
fun LocationDetailsCard(currentWeather: CurrentWeather) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.wrapContentWidth()
    ) {
        Text(
            "${currentWeather.cityName}, ${currentWeather.date}",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 12.sp
        )
    }
}