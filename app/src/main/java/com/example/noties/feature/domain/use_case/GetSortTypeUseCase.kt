package com.example.noties.feature.domain.use_case

import com.example.noties.feature.domain.model.SortType
import com.example.noties.feature.domain.repository.PrefDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSortTypeUseCase @Inject constructor(
    private val pref: PrefDataRepository
) {

     operator fun invoke(): Flow<SortType> {
        return pref.getSortPref()
    }
}