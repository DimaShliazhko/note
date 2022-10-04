package com.example.noties.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.noties.common.utils.AlarmUtils
import com.example.noties.feature.data.data_source.NoteDataBase
import com.example.noties.feature.data.repository.NoteRepositoryImpl
import com.example.noties.feature.data.repository.PrefDataRepositoryImpl
import com.example.noties.feature.domain.repository.NoteRepository
import com.example.noties.feature.domain.repository.PrefDataRepository
import com.example.noties.feature.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataBase(app: Application): NoteDataBase {
        return Room.databaseBuilder(
            app,
            NoteDataBase::class.java,
            "note"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepositoryImpl(dataBase: NoteDataBase): NoteRepository {
        return NoteRepositoryImpl(dataBase.noteDao())
    }

    @Provides
    @Singleton
    fun provideNotesUseCase(noteRepository: NoteRepository, alarmUtil: AlarmUtils): NoteUseCase {
        return NoteUseCase(
            getNotesUseCase = GetNotesUseCase(noteRepository),
            deleteNotesUseCase = DeleteNotesUseCase(noteRepository, alarmUtil),
            getNoteByIdUseCase = GetNoteByIdUseCase(noteRepository),
            insertNotesUseCase = InsertNotesUseCase(noteRepository),
            editNotesUseCase = EditNotesUseCase(noteRepository)
        )
    }

    @Provides
    @Singleton
    fun provideSortUseCase(prefDataRepository: PrefDataRepository): SortUseCase {
        return SortUseCase(
            setSortUseCase = SetSortTypeUseCase(prefDataRepository),
            getSortUseCase = GetSortTypeUseCase(prefDataRepository),
        )
    }


    @Provides
    @Singleton
    fun provideAlarmUtils(context: Context): AlarmUtils {
        return AlarmUtils(context)
    }


    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext app: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { app.preferencesDataStoreFile(PREFERENCES) }
        )
    }

}

private const val PREFERENCES = "preferences"