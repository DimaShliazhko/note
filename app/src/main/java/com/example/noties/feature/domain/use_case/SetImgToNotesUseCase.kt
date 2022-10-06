package com.example.noties.feature.domain.use_case

import android.net.Uri
import com.example.noties.feature.domain.model.Note
import com.example.noties.feature.domain.model.toNoteEntity
import com.example.noties.feature.domain.repository.NoteRepository
import javax.inject.Inject

class SetImgToNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
) {
    suspend operator fun invoke(id: Long, uri: Uri) {
        noteRepository.setImgUri(id, uri.toString())
    }
}
