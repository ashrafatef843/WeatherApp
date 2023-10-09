package com.example.weatherapp.data.dto


data class CurrentWeather(
    val id: Int,
    val name: String,
    val dt: Long,
    val main: WeatherInfo,
    val weather: List<WeatherType>,
    val wind: Wind
)

data class WeatherInfo (
    val temp: Float,
    val feelsLike: Float,
    val tempMin: Float,
    val tempMax: Float,
    val pressure: Int,
    val humidity: Int,
    val dt: Long,
)

data class Wind (
    val speed: Double
)

data class WeatherType(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
