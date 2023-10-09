package com.example.weatherapp.common.data.apis

import com.example.weatherapp.common.const.API_KEY
import com.example.weatherapp.common.const.UNIT
import com.example.weatherapp.data.dto.CurrentWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApis {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lng: Double,
        @Query("units") unit: String = UNIT,
        @Query("appid") apiKey: String = API_KEY
    ): CurrentWeather
}
