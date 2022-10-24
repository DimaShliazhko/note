package com.example.noties.feature.presentation.add_edit_notes

import androidx.lifecycle.viewModelScope
import com.example.noties.common.base.BaseViewModel
import com.example.noties.common.utils.AlarmUtils
import com.example.noties.feature.domain.model.Note
import com.example.noties.feature.domain.use_case.NoteUseCase
import com.example.noties.feature.presentation.notes.camera.CameraEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    private val alarmUtil: AlarmUtils,
) : BaseViewModel<EditNoteEvent, EditNoteState, EditNoteAction>() {

    init {
    }


    private fun getNoteById(noteId: Long) {
        viewModelScope.launch {
            val note = noteUseCase.getNoteByIdUseCase(noteId)
            _state.value = _state.value.copy(
                content = note.content,
                id = note.id,
                title = note.title,
                notificationTime = note.notificationTime,
                color = note.color,
                uri = note.uri
            )
        }
    }

    override fun createInitialState() = EditNoteState()

    override fun handleEvent(event: EditNoteEvent) {
        when (event) {
            is EditNoteEvent.LoadNote -> {
                getNoteById(event.noteId)
            }
            is EditNoteEvent.TimePick -> {
                _state.value = _state.value.copy(notificationTime = event.time)
            }
            is EditNoteEvent.AddTitle -> {
                _state.value = _state.value.copy(title = event.title)
            }
            is EditNoteEvent.AddContent -> {
                _state.value = _state.value.copy(content = event.content)
            }
            is EditNoteEvent.AddColor -> {
                _state.value = _state.value.copy(color = event.color)
            }
            is EditNoteEvent.DeleteTimer -> {
                _state.value = _state.value.copy(notificationTime = null)
            }
            is EditNoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    val note = Note(
                        id = _state.value.id,
                        title = _state.value.title,
                        content = _state.value.content,
                        notificationTime = _state.value.notificationTime,
                        color = _state.value.color,
                        addTime = System.currentTimeMillis(),
                        uri = _state.value.uri,
                    )
                    noteUseCase.deleteNotesUseCase(note = note)
                }
            }
            is EditNoteEvent.SaveNote -> {
                val note = Note(
                    id = _state.value.id,
                    title = _state.value.title,
                    content = _state.value.content,
                    notificationTime = _state.value.notificationTime,
                    color = _state.value.color,
                    addTime = System.currentTimeMillis(),
                    uri = _state.value.uri,
                )
                _state.value.id?.let {
                    viewModelScope.launch {
                        noteUseCase.editNotesUseCase(note = note)
                    }
                } ?: run {
                    viewModelScope.launch {
                        noteUseCase.insertNotesUseCase(note)
                    }
                }

                if (_state.value.notificationTime != null) {
                    if (_state.value.notificationTime!! >= System.currentTimeMillis()) {
                        setAlarm(_state.value.notificationTime!!, _state.value)
                    }
                }
            }
            is EditNoteEvent.SetUri ->{
                viewModelScope.launch {
                    noteUseCase.setImgToNotesUseCase(id = event.id, uri = event.uri)
                    _state.value = _state.value.copy(uri = event.uri)
                }
            }
        }
    }

    private fun setAlarm(notificationTime: Long, note: EditNoteState) {
        alarmUtil.createAlarm(notificationTime, note.title, note.id!!)
    }
}
