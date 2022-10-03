package com.example.noties.feature.presentation.notes

import androidx.lifecycle.viewModelScope
import com.example.noties.common.base.BaseViewModel
import com.example.noties.feature.domain.model.Note
import com.example.noties.feature.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase
) : BaseViewModel<NotesEvent, NotesState, NotesAction>() {

    init {
        _state.value = _state.value.copy(isLoading = true)
        getNotes()
    }

    private fun getNotes() {
        noteUseCase.getNotesUseCase().onEach { notes ->
            delay(2000L)
            _state.value = _state.value.copy(notes = notes, isLoading = false)
        }.launchIn(viewModelScope)
    }

    private var lastDeleteNote: Note? = null
    override fun createInitialState() = NotesState()

    override fun handleEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCase.deleteNotesUseCase(note = event.note)
                    lastDeleteNote = event.note
                }
            }
            is NotesEvent.Search -> {
                _state.value = _state.value.copy(searchText = event.title)
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCase.insertNotesUseCase(lastDeleteNote ?: return@launch)
                    lastDeleteNote = null
                }
            }
        }
    }
}