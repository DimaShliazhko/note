package com.example.noties.feature.domain.use_case

import com.example.noties.feature.domain.model.SortType
import com.example.noties.feature.domain.repository.PrefDataRepository
import javax.inject.Inject

class SetSortTypeUseCase @Inject constructor(
    private val pref: PrefDataRepository
) {

    suspend operator fun invoke(sortType: SortType) {
        pref.setSortPref(sortType)
    }
}