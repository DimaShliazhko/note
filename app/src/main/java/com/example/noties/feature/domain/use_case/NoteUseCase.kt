package com.example.noties.feature.domain.use_case

data class NoteUseCase(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNotesUseCase: DeleteNotesUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val insertNotesUseCase: InsertNotesUseCase,
    val editNotesUseCase: EditNotesUseCase,
    val deleteAllNotesUseCase: DeleteAllNotesUseCase,
    val setImgToNotesUseCase: SetImgToNotesUseCase,
  )