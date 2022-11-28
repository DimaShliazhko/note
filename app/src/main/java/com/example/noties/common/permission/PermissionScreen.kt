package com.example.noties.common.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionScreen(
    navController: NavHostController,
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO,
        )
    )
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->

            if (event == Lifecycle.Event.ON_RESUME) {
                permissionState.launchMultiplePermissionRequest()
            }

        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        permissionState.permissions.forEach { permission ->
            when (permission.permission) {
                android.Manifest.permission.CAMERA -> {
                    when {
                        permission.status.isGranted -> {
                            Text(text = "camera permission is accepted")
                        }
                        permission.status.shouldShowRationale -> {
                            Text(text = "camera permission need access the camera")
                        }
                        !permission.status.isGranted && !permission.status.shouldShowRationale -> {
                            Text(text = "camera permission denied ")
                        }
                    }
                }
                android.Manifest.permission.RECORD_AUDIO -> {
                    when {
                        permission.status.isGranted -> {
                            Text(text = "AUDIO permission is accepted")
                        }
                        permission.status.shouldShowRationale -> {
                            Text(text = "AUDIO permission need access the camera")
                        }
                        !permission.status.isGranted && !permission.status.shouldShowRationale -> {
                            Text(text = "AUDIO permission denied ")
                        }
                    }
                }

            }
        }
    }
}