package com.example.weatherapp.data.remote

import com.example.weatherapp.common.CoroutineScopeDispatchers
import com.example.weatherapp.common.data.apis.WeatherApis
import com.example.weatherapp.common.data.errors.handleHttpException
import com.example.weatherapp.data.dto.CurrentWeather
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface WeatherRemoteDataSource {
    suspend fun getCurrentWeather(
        lat: Double,
        lng: Double
    ): CurrentWeather
}
class WeatherRemoteDataSourceImpl @Inject constructor(
    private val weatherApis: WeatherApis,
    private val coroutineScopeDispatchers: CoroutineScopeDispatchers
) : WeatherRemoteDataSource {

    override suspend fun getCurrentWeather(
        lat: Double,
        lng: Double
    ): CurrentWeather {
        return withContext(coroutineScopeDispatchers.IO) {
            try {
                weatherApis.getCurrentWeather(lat, lng)
            } catch (e: Exception) {
                throw e.handleHttpException()
            }
        }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface WeatherRemoteDataSourceModule {
    @Binds
    fun provideWeatherRemoteDataSource(
        weatherRemoteDataSourceImpl: WeatherRemoteDataSourceImpl
    ): WeatherRemoteDataSource
}
