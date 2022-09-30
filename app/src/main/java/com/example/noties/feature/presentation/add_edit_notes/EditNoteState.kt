package com.example.noties.feature.presentation.add_edit_notes

import androidx.compose.ui.graphics.toArgb
import com.example.noties.common.base.State
import com.example.noties.feature.domain.model.Note

data class EditNoteState(
    val isLoading: Boolean = false,
    val id: Long? = null,
    val title: String = "",
    val content: String = "",
    val color: Int = Note.listColors.random().toArgb(),
    val addTime: Long? = null,
    var notificationTime: Long? = null
) : State {
}