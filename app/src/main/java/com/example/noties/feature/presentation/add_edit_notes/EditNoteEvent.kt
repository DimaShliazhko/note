package com.example.noties.feature.presentation.add_edit_notes

import com.example.noties.common.base.Event
import com.example.noties.feature.domain.model.Note

sealed class EditNoteEvent : Event {
    data class LoadNote(val noteId: Long) : EditNoteEvent()
    data class TimePick(val time: Long) : EditNoteEvent()
    data class AddTitle(val title: String) : EditNoteEvent()
    data class AddContent(val content: String) : EditNoteEvent()
    data class AddColor(val color: Int) : EditNoteEvent()
    object DeleteTimer : EditNoteEvent()
    object SaveNote : EditNoteEvent()
}