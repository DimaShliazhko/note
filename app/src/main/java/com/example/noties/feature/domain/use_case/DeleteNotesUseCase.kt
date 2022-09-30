package com.example.noties.feature.domain.use_case

import com.example.noties.common.utils.AlarmUtils
import com.example.noties.feature.domain.model.Note
import com.example.noties.feature.domain.model.toNote
import com.example.noties.feature.domain.model.toNoteEntity
import com.example.noties.feature.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val alarmUtil: AlarmUtils,
) {

    suspend operator fun invoke(note: Note) {
        alarmUtil.cancelAlarm(note.id!!)
        noteRepository.deleteNotes(note.toNoteEntity())
    }
}
