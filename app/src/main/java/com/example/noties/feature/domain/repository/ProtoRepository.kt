package com.example.noties.feature.domain.repository

import com.example.noties.Person
import kotlinx.coroutines.flow.Flow

interface ProtoRepository {

    val readProto: Flow<Person>

    suspend fun updateValue(firstname : String)
}