package com.example.weatherapp.domain.location

import com.example.weatherapp.data.location.LocationStatus

interface LocationTracker {
    suspend fun getCurrentLocation(): LocationStatus
}
