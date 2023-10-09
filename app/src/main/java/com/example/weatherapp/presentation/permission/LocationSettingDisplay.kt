package com.example.weatherapp.presentation.permission

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task

@Composable
fun LocationSettingDisplay(onLocationSettingEnabled: () -> Unit) {
    val context: Context = LocalContext.current

    val settingResultRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK)
            onLocationSettingEnabled.invoke()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            modifier = Modifier.wrapContentSize(),
            textAlign = TextAlign.Center,
            color = Color.White,
            text = stringResource(R.string.msg_needed_gps)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                openLocationService(context, settingResultRequest, onLocationSettingEnabled)
            },
        ) {
            Text(stringResource(R.string.title_open_gps))
        }
    }
}


fun openLocationService(
    context: Context,
    settingResultRequest: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
    onLocationSettingEnabled: () -> Unit
) {
    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
    val locationSettingsRequest =
        LocationSettingsRequest.Builder().addLocationRequest(locationRequest.build())

    val gpsSettingTask: Task<LocationSettingsResponse> =
        client.checkLocationSettings(locationSettingsRequest.build())

    gpsSettingTask.addOnSuccessListener {
        onLocationSettingEnabled.invoke()
    }
    gpsSettingTask.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                val intentSenderRequest =
                    IntentSenderRequest.Builder(exception.resolution).build()
                settingResultRequest.launch(intentSenderRequest)
            } catch (sendEx: IntentSender.SendIntentException) {
                // ignore here
            }
        }
    }
}
