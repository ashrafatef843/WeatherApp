package com.example.weatherapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.common.presentation.CircularLoading
import com.example.weatherapp.common.presentation.RetryItem
import com.example.weatherapp.common.presentation.dto.Fail
import com.example.weatherapp.common.presentation.dto.Loading
import com.example.weatherapp.common.presentation.dto.Success
import com.example.weatherapp.common.presentation.handleHttpError
import com.example.weatherapp.common.presentation.theme.DarkGray
import com.example.weatherapp.data.location.LocationStatus
import com.example.weatherapp.presentation.permission.LocationPermissionDisplay
import com.example.weatherapp.presentation.permission.LocationSettingDisplay
import com.example.weatherapp.presentation.permission.UndetectableLocationDisplay
import com.example.weatherapp.presentation.weather.WeatherCard
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination(start = true)
fun WeatherScreen(
    weatherViewModel: WeatherViewModel = hiltViewModel()
) {
    val locationStatus = weatherViewModel.state.locationStatus
    val refreshCallback: ()-> Unit = {
        weatherViewModel.refresh()
    }
    Surface(
        Modifier
            .fillMaxSize(),
        color = DarkGray
    ) {
        when (locationStatus) {
            is LocationStatus.LocationLoading -> CircularLoading()
            is LocationStatus.LocationSettingDisabled -> LocationSettingDisplay(refreshCallback)
            is LocationStatus.LocationPermissionDenied -> LocationPermissionDisplay(refreshCallback)
            is LocationStatus.LocationUndetectable -> UndetectableLocationDisplay(refreshCallback)
            else -> {}
        }

        when (val currentWeather = weatherViewModel.state.currentWeather) {
            is Loading -> CircularLoading()
            is Success -> WeatherCard(currentWeather = currentWeather(), refreshCallback)
            is Fail -> {
                LocalContext.current.handleHttpError(currentWeather.error)
                RetryItem {
                    weatherViewModel.refresh()
                }
            }
            else -> {}
        }
    }
}
