package com.example.noties.feature.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.example.noties.Person
import com.example.noties.feature.domain.repository.ProtoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class ProtoRepositoryImpl @Inject constructor(
    private val protoDataStore: DataStore<Person>,
) : ProtoRepository {
    override val readProto: Flow<Person> = protoDataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(Person.getDefaultInstance())
        } else {
            throw exception
        }
    }

    override suspend fun updateValue(firstname: String) {
        protoDataStore.updateData { person ->
            person.toBuilder().setFirstname(firstname).build()
        }
    }
}