package com.example.weatherapp.presentation

import com.example.weatherapp.common.presentation.dto.Async
import com.example.weatherapp.common.presentation.dto.Uninitialized
import com.example.weatherapp.data.dto.CurrentWeather
import com.example.weatherapp.data.location.LocationStatus

data class WeatherState(
    val locationStatus: LocationStatus = LocationStatus.Uninitialized,
    val currentWeather: Async<CurrentWeather> = Uninitialized
)
