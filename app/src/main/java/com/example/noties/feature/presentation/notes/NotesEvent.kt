package com.example.noties.feature.presentation.notes

import com.example.noties.common.base.Event
import com.example.noties.feature.domain.model.Note

sealed class NotesEvent : Event {
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
}