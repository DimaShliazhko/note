package com.example.noties.feature.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.noties.feature.data.repository.DataStoreKey.FIELD_DATE
import com.example.noties.feature.data.repository.DataStoreKey.FIELD_TITLE
import com.example.noties.feature.domain.model.SortType
import com.example.noties.feature.domain.repository.PrefDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrefDataRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PrefDataRepository {

    override suspend fun setSortPref(pref: SortType) {
        dataStore.edit { preferences ->
            preferences[FIELD_TITLE] = pref.sortByTitle
            preferences[FIELD_DATE] = pref.sortByDate
        }
    }

    override fun getSortPref(): Flow<SortType> {
        return dataStore.data.map {
            val title = it[FIELD_TITLE] ?: true
            val date = it[FIELD_DATE] ?: false
            SortType(sortByTitle = title, sortByDate = date)
        }
    }
}

object DataStoreKey {
    val FIELD_TITLE = booleanPreferencesKey("sort_title")
    val FIELD_DATE = booleanPreferencesKey("sort_date")
}