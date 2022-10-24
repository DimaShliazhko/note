package com.example.noties

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.noties.common.navigation.Navigation
import com.example.noties.common.utils.exception.GlobalExceptionHandler
import com.example.noties.common.utils.exception.checkPermissions
import com.example.noties.feature.presentation.notes.dialog.ErrorDialog
import com.example.noties.ui.theme.NotiesTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {

    }
    private val requiresPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {

        } else {

        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp = this.checkPermissions()
        if (temp.isNotEmpty()) {
            permissionLauncher.launch(temp.toTypedArray())
        }



        setContent {
            NotiesTheme {
                val navController = rememberAnimatedNavController()
                var showErrorDialog by remember { mutableStateOf(false) }
                GlobalExceptionHandler.getThrowableFromIntent(intent)?.let {
                    if (!showErrorDialog) {
                        ErrorDialog(it, onDismiss = { showErrorDialog = it })
                    }

                }
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    Navigation(navController)
                }
            }
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) -> {}
            else -> requiresPermission.launch(Manifest.permission.CAMERA)
        }

    }
}

