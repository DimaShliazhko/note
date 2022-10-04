package com.example.noties.feature.presentation.notes

import androidx.lifecycle.viewModelScope
import com.example.noties.common.base.BaseViewModel
import com.example.noties.feature.domain.model.Note
import com.example.noties.feature.domain.use_case.NoteUseCase
import com.example.noties.feature.domain.use_case.SortUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    private val prefUseCase: SortUseCase,
) : BaseViewModel<NotesEvent, NotesState, NotesAction>() {

    init {
        getNotes()
    }

    private fun getNotes() {

        prefUseCase.getSortUseCase().onEach {
            _state.value = _state.value.copy(sortType = it)
            getItem()
        }.launchIn(viewModelScope)

    }

    private fun getItem() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            noteUseCase.getNotesUseCase().onEach { notes ->
                delay(2000L)
                val temp = if (_state.value.sortType.sortByTitle) {
                    notes.sortedBy { it.title.lowercase() }
                } else {
                    notes.sortedBy { it.addTime }.reversed()
                }
                _state.value = _state.value.copy(notes = temp, isLoading = false)
            }.launchIn(viewModelScope)

        }
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
            is NotesEvent.DeleteAllNote -> {
                viewModelScope.launch {
                    noteUseCase.deleteAllNotesUseCase()
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCase.insertNotesUseCase(lastDeleteNote ?: return@launch)
                    lastDeleteNote = null
                }
            }

            is NotesEvent.SortNote -> {
                viewModelScope.launch {
                    prefUseCase.setSortUseCase(event.note)
                    setAction(NotesAction.ScrollListUp)
                }
            }
        }
    }
}