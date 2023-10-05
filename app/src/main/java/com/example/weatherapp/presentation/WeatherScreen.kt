package com.example.weatherapp.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.common.presentation.CircularLoading
import com.example.weatherapp.data.LocationStatus
import com.example.weatherapp.permission.LocationPermissionDisplay
import com.example.weatherapp.permission.LocationSettingDisplay
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun WeatherScreen(
    weatherViewModel: WeatherViewModel = hiltViewModel()
) {
    val locationStatus = weatherViewModel.state.locationStatus
    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = weatherViewModel::refresh
    ) {
        Surface(
            Modifier
                .fillMaxSize(),
            color = Color.DarkGray
        ) {
            when (locationStatus) {
                is LocationStatus.LocationLoading -> CircularLoading()
                is LocationStatus.LocationSettingDisabled -> LocationSettingDisplay {
                    weatherViewModel.refresh()
                }

                is LocationStatus.LocationPermissionDenied -> LocationPermissionDisplay {
                    weatherViewModel.refresh()
                }

                is LocationStatus.LocationDetected ->
                    Toast.makeText(
                        LocalContext.current,
                        locationStatus.location.toString(),
                        Toast.LENGTH_LONG
                    ).show()

                else -> {}
            }
        }
    }
}