package com.example.noties.feature.presentation.add_edit_notes

import androidx.lifecycle.viewModelScope
import com.example.noties.common.base.BaseViewModel
import com.example.noties.common.utils.AlarmUtils
import com.example.noties.feature.domain.model.Note
import com.example.noties.feature.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    private val alarmUtil: AlarmUtils,
) : BaseViewModel<EditNoteEvent, EditNoteState, EditNoteAction>() {

    init {
        // getNoteById()
    }

    private fun getNoteById(note: Note) {
        viewModelScope.launch {
            _state.value = _state.value.copy(note = note)
            _state.value = _state.value.copy(notificationTime = note.notificationTime)
        }
    }

    override fun createInitialState() = EditNoteState()

    override fun handleEvent(event: EditNoteEvent) {
        when (event) {
            is EditNoteEvent.LoadNote -> {
                getNoteById(event.note)
            }
            is EditNoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCase.deleteNotesUseCase(note = event.note)
                }
            }
            is EditNoteEvent.TimePick -> {
                _state.value = state.value.copy(notificationTime = event.time)
            }
            is EditNoteEvent.SaveNote -> {

                event.note.id?.let {
                    viewModelScope.launch {
                        noteUseCase.editNotesUseCase(event.note.copy(notificationTime = state.value.notificationTime))
                    }
                } ?: run {
                    viewModelScope.launch {
                        noteUseCase.insertNotesUseCase(event.note.copy(notificationTime = state.value.notificationTime))
                    }
                }

                state.value.notificationTime?.let { setAlarm(it) }
            }
        }

    }

    private fun setAlarm(notificationTime: Long) {
        alarmUtil.createAlarm(notificationTime)
    }
}
