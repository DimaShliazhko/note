package com.example.noties.feature.presentation.notes.datastore

import com.example.noties.common.base.Event

sealed class DataStoreEvent() : Event {
    data class SetValue(val firsName: String) : DataStoreEvent()
}