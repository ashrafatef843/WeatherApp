package com.example.weatherapp.data

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

interface LocationTracker {
    suspend fun getCurrentLocation(): LocationStatus
}

@ExperimentalCoroutinesApi
class LocationTrackerImpl @Inject constructor(
    private val application: Application
) : LocationTracker {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): LocationStatus {
        return if (isPermissionDenied())
            LocationStatus.LocationPermissionDenied
        else if (isLocationProviderDisabled())
            LocationStatus.LocationSettingDisabled
        else {
            val locationClient = LocationServices.getFusedLocationProviderClient(application)
            val location = locationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token
            ).await()

            if (location == null)
                LocationStatus.LocationUndetectable
            else
                LocationStatus.LocationDetected(location)
        }
    }

    private fun isPermissionDenied() =
        ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_DENIED

    private fun isLocationProviderDisabled(): Boolean {
        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}

sealed class LocationStatus {

    object Uninitialized : LocationStatus()

    object LocationLoading : LocationStatus()
    object LocationSettingDisabled : LocationStatus()

    object LocationPermissionDenied : LocationStatus()

    data class LocationDetected(
        val location: Location
    ) : LocationStatus()

    object LocationUndetectable : LocationStatus()
}