package com.example.weatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.CoroutineScopeDispatchers
import com.example.weatherapp.common.presentation.BaseViewModel
import com.example.weatherapp.common.presentation.dto.Async
import com.example.weatherapp.common.presentation.dto.Uninitialized
import com.example.weatherapp.data.location.LocationStatus
import com.example.weatherapp.data.dto.CurrentWeather
import com.example.weatherapp.domain.CurrentWeatherUseCase
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCase: CurrentWeatherUseCase,
    private val locationTracker: LocationTracker,
    private val coroutineScopeDispatchers: CoroutineScopeDispatchers
) : BaseViewModel(coroutineScopeDispatchers) {

    var state by mutableStateOf(WeatherState())
        private set

    init {
        getLocation()
    }
    fun refresh() {
        getLocation()
    }
    private fun getLocation() {
        state = state.copy(locationStatus = LocationStatus.LocationLoading)
        viewModelScope.launch(coroutineScopeDispatchers.IO) {
            val locationResult = locationTracker.getCurrentLocation()
            withContext(coroutineScopeDispatchers.Main) {
                state = state.copy(locationStatus = locationResult)

                if (locationResult is LocationStatus.LocationDetected)
                    getWeather(locationResult.location.lat, locationResult.location.lng)
            }
        }
    }

     private fun getWeather(lat: Double, lng: Double) {
        suspend {
            weatherUseCase(lat, lng)
        }.execute {
            state = state.copy(
                currentWeather = it
            )
        }
    }
}
