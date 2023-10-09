package com.example.weatherapp.presentation.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.weatherapp.R

@Composable
fun LocationPermissionDisplay(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_DENIED ||
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_DENIED
    ) {
        val locationPermissionRequest = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (
                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    false
                ) &&
                permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false)
            )
                onPermissionGranted.invoke()
        }
       PermissionsRevoked(locationPermissionRequest)
    } else
        onPermissionGranted.invoke()
}

@Composable
fun PermissionsRevoked(
    locationPermissionRequest:
    ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {
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
            text = stringResource(R.string.msg_needed_location_permissions)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            locationPermissionRequest.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }) {
            Text(stringResource(R.string.msg_request_permission))
        }
    }
}
