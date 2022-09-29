package com.example.noties.feature.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noties.feature.data.model.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}