package com.example.noties.feature.presentation.notes.video

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.example.noties.common.base.BaseViewModel
import com.example.noties.feature.domain.model.VideoItem
import com.example.noties.feature.domain.model.metadatas.MetaDataReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val player: Player,
    private val metaDataReader: MetaDataReader,
) : BaseViewModel<VideoEvent, VideoState, VideoAction>() {

    //  private val videoUris = savedStateHandle.getStateFlow("videoUris", emptyList<Uri>())

/*    val videoItems = videoUris.map { uris ->
        uris.map { uri ->
            VideoItem(
                contentUri = uri,
                mediaItem = MediaItem.fromUri(uri),
                name = metaDataReader.getMetadataFromUri(uri)?.fileName ?: "No name"
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())*/


    init {
        player.prepare()
    }

    fun addVideoUri(uri: Uri) {
        var items = _state.value.videoItems.toMutableList()
        items.add(
            VideoItem(
                contentUri = uri,
                mediaItem = MediaItem.fromUri(uri),
                name = metaDataReader.getMetadataFromUri(uri)?.fileName ?: "No name"
            )
        )
        _state.value = _state.value.copy(
            videoItems = items
        )
        player.addMediaItem(MediaItem.fromUri(uri))
    }

    fun playVideoUri(uri: Uri) {
        player.setMediaItem(
            _state.value.videoItems.find {
                it.contentUri == uri
            }?.mediaItem ?: return
        )
        player.play()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    override fun createInitialState(): VideoState {

        return VideoState(videoItems = emptyList())
    }

    override fun handleEvent(event: VideoEvent) {
        when (event) {
            else -> Unit
        }
    }

    companion object {
        private const val VIDEO_KEY = "videoUris"
    }
}
