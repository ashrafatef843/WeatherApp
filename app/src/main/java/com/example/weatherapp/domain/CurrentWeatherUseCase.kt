package com.example.weatherapp.domain

import com.example.weatherapp.data.dto.CurrentWeather
import com.example.weatherapp.domain.repo.WeatherRepository
import javax.inject.Inject

class CurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        lat: Double,
        lng: Double
    ): CurrentWeather = weatherRepository.getCurrentWeather(lat, lng)
}