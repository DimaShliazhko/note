package com.example.noties.feature.presentation.notes.camera

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.noties.feature.presentation.add_edit_notes.EditNoteEvent
import com.example.noties.ui.theme.Purple500

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CameraScreen(
    navController: NavHostController,
    noteId: Long,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var photoUri by remember { mutableStateOf("".toUri()) }
    val scaffoldState = rememberScaffoldState()
    var shouldShowCamera by remember { mutableStateOf(true) }
    var shouldShowPhoto by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = noteId) {
        viewModel.setEvent(CameraEvent.LoadNote(noteId))
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (shouldShowPhoto) {
                CameraTopBar(onSaveClick = {
                    viewModel.setEvent(CameraEvent.SetUri(uri = photoUri, id = noteId))
                    navController.navigateUp()
                })
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple500)
        ) {
            if (shouldShowCamera) {
                CameraView(
                    outputDirectory = state.directory!!,
                    executor = state.cameraExecutor!!,
                    onImageCaptured = {
                        shouldShowCamera = false
                        photoUri = it
                        shouldShowPhoto = true
                    },
                    onError = {}
                )
            }
            if (shouldShowPhoto) {
                Image(
                    painter = rememberImagePainter(photoUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

            }
        }
    }
}