package com.example.noties.feature.presentation.notes.datastore

import com.example.noties.common.base.State
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

data class DataStoreState(
    val isLoad: Boolean = true,
    val firstName: String = ""
) : State