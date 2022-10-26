package com.example.noties.feature.presentation.notes.video

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController

@Composable
fun VideoScreen(
    navController: NavHostController,
    viewModel: VideoViewModel = hiltViewModel()
) {
    val selectedVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> uri?.let(viewModel::addVideoUri) })

    var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
    var lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).also {
                    it.player = viewModel.player
                }
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                    }
                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                        it.player?.play()
                    }
                    else -> Unit
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        IconButton(onClick = { selectedVideoLauncher.launch("video/mp4") }) {
            Icon(imageVector = Icons.Default.FileUpload, contentDescription = "")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(viewModel.state.value.videoItems) { item ->
                Text(
                    text = item.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.playVideoUri(item.contentUri) }
                        .padding(8.dp)
                )
            }
        }

    }
}