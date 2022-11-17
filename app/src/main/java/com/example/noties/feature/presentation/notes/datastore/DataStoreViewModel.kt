package com.example.noties.feature.presentation.notes.datastore

import androidx.lifecycle.viewModelScope
import com.example.noties.common.base.BaseViewModel
import com.example.noties.feature.domain.repository.ProtoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(private val protoRepository: ProtoRepository) :
    BaseViewModel<DataStoreEvent, DataStoreState, DataStoreAction>() {
    override fun createInitialState() = DataStoreState()

    init {
        viewModelScope.launch {
            val firstName = protoRepository.readProto.collect {
                _state.value = _state.value.copy(isLoad = false, firstName = it.firstname)
            }
        }


    }

    override fun handleEvent(event: DataStoreEvent) {
        when (event) {
            is DataStoreEvent.SetValue -> {
                viewModelScope.launch(Dispatchers.IO) {
                    protoRepository.updateValue(event.firsName)
                }
            }
        }
    }
}