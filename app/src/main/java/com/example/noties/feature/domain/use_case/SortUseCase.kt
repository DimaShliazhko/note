package com.example.noties.feature.domain.use_case

data class SortUseCase(
    val getSortUseCase: GetSortTypeUseCase,
    val setSortUseCase: SetSortTypeUseCase,
)