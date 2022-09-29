package com.example.noties.feature.presentation.add_edit_notes

import com.example.noties.common.base.State
import com.example.noties.feature.domain.model.Note

data class EditNoteState(
    val isLoading: Boolean = false,
    var note: Note? = null,
    var notificationTime: Long? = null
) : State {
}