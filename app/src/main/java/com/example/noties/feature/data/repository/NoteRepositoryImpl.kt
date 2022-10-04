package com.example.noties.feature.data.repository

import com.example.noties.feature.data.data_source.NoteDao
import com.example.noties.feature.data.model.NoteEntity
import com.example.noties.feature.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<NoteEntity>> {
        return noteDao.getNotes()
    }

    override suspend fun getNotesById(id: Long): NoteEntity {
        return noteDao.getNoteById(id)
    }

    override suspend fun insertNotes(note: NoteEntity) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNotes(note: NoteEntity) {
        noteDao.deleteNote(note)
    }

    override suspend fun deleteAllNotes(id :List<Long>) {
        noteDao.deleteAllNote(id)
    }

    override suspend fun editNotes(note: NoteEntity) {
        noteDao.updateNote(note)
    }
}