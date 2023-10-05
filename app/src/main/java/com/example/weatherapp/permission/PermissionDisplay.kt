package com.example.weatherapp.permission

import com.example.weatherapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun LocationPermissionDisplay(onPermissionGranted: () -> Unit) {
    val locationPermissionState = rememberPermissionState(
            android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    if (locationPermissionState.status.isGranted) {
        onPermissionGranted.invoke()
    } else {
        PermissionsRevoked(locationPermissionState)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsRevoked(locationPermissionState: PermissionState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painterResource(R.drawable.ic_icon), "pin",
            Modifier
                .width(100.dp)
                .height(100.dp)
        )
        Text(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally),
            color = Color.White,
            textAlign = TextAlign.Center,
            text = if (locationPermissionState.status.shouldShowRationale) {
                "The location is important for this app. Please grant the permission."
            } else {
                "Location permission required for feature to be available Please grant the permission"
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
            Text("Request permission")
        }
    }
}