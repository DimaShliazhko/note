package com.example.noties.di

import com.example.noties.feature.data.repository.PrefDataRepositoryImpl
import com.example.noties.feature.domain.repository.PrefDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPrefDataRepositoryImpl(prefDataRepositoryImpl: PrefDataRepositoryImpl): PrefDataRepository

}