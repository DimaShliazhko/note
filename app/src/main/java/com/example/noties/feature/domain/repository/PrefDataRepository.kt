package com.example.noties.feature.domain.repository

import com.example.noties.feature.domain.model.SortType
import kotlinx.coroutines.flow.Flow

interface PrefDataRepository {
    suspend fun setSortPref(pref: SortType)
    fun getSortPref(): Flow<SortType>
}