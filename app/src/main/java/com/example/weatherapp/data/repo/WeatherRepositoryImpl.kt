package com.example.weatherapp.data.repo
import com.example.weatherapp.data.dto.CurrentWeather
import com.example.weatherapp.data.remote.WeatherRemoteDataSource
import com.example.weatherapp.domain.repo.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {
    override suspend fun getCurrentWeather(
        lat: Double,
        lng: Double
    ): CurrentWeather = weatherRemoteDataSource.getCurrentWeather(lat, lng)
}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface  WeatherRepoModule {
    @Binds
    fun provideWeatherRepo(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}
