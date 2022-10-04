package com.example.noties.feature.domain.use_case

import com.example.noties.common.utils.AlarmUtils
import com.example.noties.feature.domain.model.toNote
import com.example.noties.feature.domain.model.toNoteEntity
import com.example.noties.feature.domain.repository.NoteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DeleteAllNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val alarmUtil: AlarmUtils,
) {
    suspend operator fun invoke() {
        val temp = noteRepository.getNotes().map { notes ->
            notes.map { it.toNote() }
        }.first()
        temp.forEach {
            noteRepository.deleteNotes(it.toNoteEntity())
            alarmUtil.cancelAlarm(it.id!!)
        }
    }
}
