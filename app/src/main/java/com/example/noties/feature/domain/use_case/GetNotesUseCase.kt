package com.example.noties.feature.domain.use_case

import com.example.noties.feature.domain.model.Note
import com.example.noties.feature.domain.model.NoteSort
import com.example.noties.feature.domain.model.toNote
import com.example.noties.feature.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    operator fun invoke(): Flow<List<Note>> {
        return noteRepository.getNotes().map { notes ->
            notes.map { it.toNote() }
        }
    }
}



