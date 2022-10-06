package com.example.noties.feature.presentation.notes.camera

import android.net.Uri
import com.example.noties.common.base.Event

sealed class CameraEvent : Event {
    data class SetUri(val id: Long, val uri: Uri) : CameraEvent()
    data class LoadNote(val noteId: Long) : CameraEvent()
}