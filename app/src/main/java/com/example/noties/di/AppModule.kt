package com.example.noties.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import com.example.noties.Person
import com.example.noties.common.utils.AlarmUtils
import com.example.noties.common.utils.PERSON_PREFERENCES
import com.example.noties.feature.data.data_source.NoteDataBase
import com.example.noties.feature.data.repository.NoteRepositoryImpl
import com.example.noties.feature.data.repository.ProtoRepositoryImpl
import com.example.noties.feature.domain.MySerializer
import com.example.noties.feature.domain.model.metadatas.MetaDataReader
import com.example.noties.feature.domain.model.metadatas.MetaDataReaderImpl
import com.example.noties.feature.domain.repository.NoteRepository
import com.example.noties.feature.domain.repository.PrefDataRepository
import com.example.noties.feature.domain.repository.ProtoRepository
import com.example.noties.feature.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
    fun provideProtoDataStore(@ApplicationContext appContext: Context): DataStore<Person> {
        return DataStoreFactory.create(
            serializer = MySerializer(),
            corruptionHandler = null,
            //  migrations = listOf(SharedPreferencesMigration(appContext, PERSON_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.dataStoreFile(PERSON_PREFERENCES) }
        )
    }

    @Provides
    @Singleton
    fun provideProtoRepositoryImpl(protoDataStore: DataStore<Person>): ProtoRepository {
        return ProtoRepositoryImpl(protoDataStore)
    }

    @Provides
    @Singleton
    fun provideNotesUseCase(
        noteRepository: NoteRepository,
        alarmUtil: AlarmUtils,
        metaDataReader: MetaDataReader
    ): NoteUseCase {
        return NoteUseCase(
            getNotesUseCase = GetNotesUseCase(noteRepository),
            deleteNotesUseCase = DeleteNotesUseCase(noteRepository, alarmUtil),
            getNoteByIdUseCase = GetNoteByIdUseCase(noteRepository),
            insertNotesUseCase = InsertNotesUseCase(noteRepository),
            editNotesUseCase = EditNotesUseCase(noteRepository),
            deleteAllNotesUseCase = DeleteAllNotesUseCase(noteRepository, alarmUtil),
            setImgToNotesUseCase = SetImgToNotesUseCase(noteRepository),
        )
    }

    @Provides
    @Singleton
    fun provideGetOutputDirectoryUseCase(context: Context): GetOutputDirectoryUseCase {
        return GetOutputDirectoryUseCase(context)
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

    @Provides
    @Singleton
    fun provideVideoPlayer(@ApplicationContext app: Context): Player {
        return ExoPlayer.Builder(app).build()
    }


    @Provides
    @Singleton
    fun provideMetaDataReader(@ApplicationContext app: Context): MetaDataReader {
        return MetaDataReaderImpl(app)
    }

}

private const val PREFERENCES = "preferences"