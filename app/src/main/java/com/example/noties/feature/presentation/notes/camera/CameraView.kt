package com.example.noties.feature.presentation.notes.camera

import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.LENS_FACING_BACK
import androidx.camera.core.CameraSelector.LENS_FACING_FRONT
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Lens
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.noties.R
import com.example.noties.common.utils.getCameraProvider
import com.example.noties.common.utils.takePhoto
import java.io.File
import java.util.concurrent.Executor

@Composable
fun CameraView(
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val context = LocalContext.current
    var lensFacingBack by remember { mutableStateOf(true) }
    val lensFacing = if (lensFacingBack) {
        LENS_FACING_BACK
    } else {
        LENS_FACING_FRONT
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

    LaunchedEffect(key1 = lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        IconButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = {
                takePhoto(
                    fileNameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                    imageCapture = imageCapture,
                    outputDirectory = outputDirectory,
                    executor = executor,
                    onImageCaptured = onImageCaptured,
                    onError = onError
                )
            }) {
            Icon(
                imageVector = Icons.Sharp.Lens,
                contentDescription = "take picture",
                tint = Color.White,
                modifier = Modifier
                    .size(70.dp)
                    .padding(vertical = 1.dp)
                    .border(1.dp, Color.White, CircleShape)
            )
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp),
            onClick = {
                lensFacingBack = !lensFacingBack
            }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_repeat),
                contentDescription = "take picture",
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .padding(vertical = 1.dp)
            )
        }

    }
}