package com.example.noties.feature.presentation.notes

import com.example.noties.common.base.State
import com.example.noties.feature.domain.model.Note

data class NotesState(
    val isLoading: Boolean = false,
    val notes: List<Note> = emptyList()
) : State {
}