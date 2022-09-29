package com.example.noties.feature.domain.use_case

import com.example.noties.feature.domain.model.Note
import com.example.noties.feature.domain.model.toNote
import com.example.noties.feature.domain.model.toNoteEntity
import com.example.noties.feature.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InsertNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        noteRepository.insertNotes(note.toNoteEntity())
    }
}
