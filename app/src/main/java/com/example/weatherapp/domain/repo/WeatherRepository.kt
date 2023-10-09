package com.example.weatherapp.domain.repo

import com.example.weatherapp.data.dto.CurrentWeather

interface WeatherRepository {
    suspend fun getCurrentWeather(
        lat: Double,
        lng: Double
    ): CurrentWeather
}