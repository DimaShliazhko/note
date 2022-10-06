package com.example.noties.feature.presentation.notes.camera

import com.example.noties.common.base.State
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

data class CameraState(
    val id : Long? = null,
    val cameraExecutor: ExecutorService? = Executors.newSingleThreadExecutor(),
    val directory: File? = null
) : State