package com.example.noties.feature.presentation.notes.camera

import androidx.lifecycle.viewModelScope
import com.example.noties.common.base.BaseViewModel
import com.example.noties.feature.domain.use_case.GetOutputDirectoryUseCase
import com.example.noties.feature.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val getOutputDirectoryUseCase: GetOutputDirectoryUseCase,
    private val noteUseCase: NoteUseCase,
) : BaseViewModel<CameraEvent, CameraState, CameraAction>() {

    init {
        getOutputDirectory()
    }

    private fun getOutputDirectory() {
        val directory = getOutputDirectoryUseCase()
        _state.value = _state.value.copy(directory = directory)
    }


    override fun createInitialState() = CameraState()

    override fun handleEvent(event: CameraEvent) {
        when (event) {
            is CameraEvent.SetUri -> {
                viewModelScope.launch {
                    noteUseCase.setImgToNotesUseCase(id = event.id, uri = event.uri)
                }
            }
            is CameraEvent.LoadNote -> {
                _state.value = _state.value.copy(id = event.noteId)
            }
        }
    }
}
