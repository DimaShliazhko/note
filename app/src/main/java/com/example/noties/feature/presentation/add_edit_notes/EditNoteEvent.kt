package com.example.noties.feature.presentation.add_edit_notes

import com.example.noties.common.base.Event
import com.example.noties.feature.domain.model.Note

sealed class EditNoteEvent : Event {
    data class DeleteNote(val note: Note) : EditNoteEvent()
    data class LoadNote(val note: Note) : EditNoteEvent()
    data class TimePick(val time: Long) : EditNoteEvent()
    data class SaveNote(val note: Note) : EditNoteEvent()
}