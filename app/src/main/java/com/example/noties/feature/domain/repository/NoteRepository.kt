package com.example.noties.feature.domain.repository

import com.example.noties.feature.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<NoteEntity>>
    suspend fun getNotesById(id: Long): NoteEntity
    suspend fun insertNotes(note: NoteEntity)
    suspend fun deleteNotes(note: NoteEntity)
    suspend fun deleteAllNotes(id: List<Long>)
    suspend fun editNotes(note: NoteEntity)
    suspend fun setImgUri(id: Long, uri: String)
}