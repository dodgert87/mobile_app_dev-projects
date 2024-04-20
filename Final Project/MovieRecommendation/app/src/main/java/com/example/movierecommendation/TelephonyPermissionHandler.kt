package com.example.movierecommendation

import android.Manifest
import android.telephony.TelephonyManager
import android.content.Context
import androidx.compose.runtime.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TelephonyPermissionHandler(context: Context, onPermissionGranted: (String?) -> Unit) {
    val telephonyPermissionState = rememberPermissionState(permission = Manifest.permission.READ_PHONE_STATE)

    LaunchedEffect(key1 = true) {
        telephonyPermissionState.launchPermissionRequest()
    }

    LaunchedEffect(telephonyPermissionState.status) {
        if (telephonyPermissionState.status.isGranted) {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val countryCode = telephonyManager.networkCountryIso.uppercase()
            onPermissionGranted(countryCode)
        }
    }


}
