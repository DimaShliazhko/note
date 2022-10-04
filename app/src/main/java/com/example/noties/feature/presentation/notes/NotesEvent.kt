package com.example.noties.feature.presentation.notes

import com.example.noties.common.base.Event
import com.example.noties.feature.domain.model.Note
import com.example.noties.feature.domain.model.NoteSort
import com.example.noties.feature.domain.model.SortType

sealed class NotesEvent : Event {
    data class DeleteNote(val note: Note) : NotesEvent()
    object DeleteAllNote : NotesEvent()
    data class SortNote(val note: SortType) : NotesEvent()
    data class Search(val title: String) : NotesEvent()
    object RestoreNote : NotesEvent()
}