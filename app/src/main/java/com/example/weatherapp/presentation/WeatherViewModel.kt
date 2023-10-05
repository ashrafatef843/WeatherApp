package com.example.weatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.CoroutineScopeDispatchers
import com.example.weatherapp.common.presentation.BaseViewModel
import com.example.weatherapp.data.LocationStatus
import com.example.weatherapp.data.LocationTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val coroutineScopeDispatchers: CoroutineScopeDispatchers
) : BaseViewModel(coroutineScopeDispatchers) {

    var state by mutableStateOf(WeatherStatus())
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
            }

            if (locationResult is LocationStatus.LocationDetected)
                getWeather()
        }
    }

    private fun getWeather() {

    }
}

data class WeatherStatus(
    val locationStatus: LocationStatus = LocationStatus.Uninitialized
)