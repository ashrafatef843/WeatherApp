package com.example.weatherapp.presentation.weather

import com.example.weatherapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.common.const.ICON_EXTENSION
import com.example.weatherapp.common.const.ICON_URL
import com.example.weatherapp.common.presentation.theme.Purple40
import com.example.weatherapp.common.presentation.util.toTime
import com.example.weatherapp.data.dto.CurrentWeather
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlin.math.roundToInt

@Composable
fun WeatherCard(currentWeather: CurrentWeather, onRefresh: ()-> Unit) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = false),
            onRefresh = onRefresh
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.title_today, currentWeather.dt.toTime()),
                    modifier = Modifier.align(Alignment.End),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                AsyncImage(
                    model = "${ICON_URL}${currentWeather.weather.first().icon}${ICON_EXTENSION}",
                    contentDescription = "weather",
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${currentWeather.main.temp.roundToInt()}°",
                    fontSize = 70.sp,
                    color = Purple40
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = currentWeather.weather.first().description,
                    color = Color.White,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(
                        R.string.msg_weather,
                        currentWeather.main.tempMax.roundToInt(),
                        currentWeather.main.tempMin.roundToInt(),
                        currentWeather.main.feelsLike
                    ),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.wrapContentSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically),
                        contentDescription = null,
                        painter = painterResource(id = R.drawable.ic_icon)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = currentWeather.name,
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataDisplay(
                        value = currentWeather.main.pressure,
                        unit = stringResource(R.string.title_pressure_unit),
                        painter = painterResource(id = R.drawable.ic_pressure)
                    )
                    WeatherDataDisplay(
                        value = currentWeather.main.humidity,
                        unit = stringResource(R.string.title_humidity_unit),
                        painter = painterResource(id = R.drawable.ic_drop)
                    )
                    WeatherDataDisplay(
                        value = currentWeather.wind.speed.roundToInt(),
                        unit = stringResource(R.string.title_wind_unit),
                        painter = painterResource(id = R.drawable.ic_wind)
                    )
                }
            }
        }
}