package com.example.noties.feature.domain.use_case


import com.example.noties.feature.data.model.NoteEntity
import com.example.noties.feature.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock


class GetNoteByIdUseCaseTest {

    @Test
    fun test1() {
        val expected = 4
        val actual = 2 + 2
        assertEquals(expected, actual)
    }

    val repository = mock<NoteRepository>()


    @Test
    fun should_return_correct_data() {

        //or
        /*  @Mock
          lateinit var repository: NoteRepository*/

        runBlocking {
            Mockito.`when`(repository.getNotesById(136))
                .thenReturn(
                    NoteEntity(
                        id = 136, title = "TEst", content = "content", uri = "//", addTime = 11, color
                        = 111, notificationTime = 11
                    )
                )
            val useCase = GetNoteByIdUseCase(noteRepository = repository)
            val expected = useCase(136)

            val actual = NoteEntity(
                id = 136, title = "Ttst title", content = "Text Content", uri = "uri", addTime = 121212L, color = 12,
                notificationTime = 121212L
            )

            assertEquals(expected, actual)
        }

    }
}