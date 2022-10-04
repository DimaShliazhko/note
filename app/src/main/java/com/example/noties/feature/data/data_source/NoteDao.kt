package com.example.noties.feature.data.data_source

import androidx.room.*
import com.example.noties.feature.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Long): NoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("DELETE  FROM note WHERE id IN (:idList)")
    suspend fun deleteAllNote(idList: List<Long>)

    @Update
    suspend fun updateNote(note: NoteEntity)
}