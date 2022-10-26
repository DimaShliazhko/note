package com.example.noties.feature.presentation.notes.video

import android.net.Uri
import com.example.noties.common.base.Event
import com.example.noties.feature.presentation.notes.camera.CameraEvent

sealed class VideoEvent : Event {
    data class SetUri(val id: Long, val uri: Uri) : VideoEvent()
}