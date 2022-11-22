package com.example.noties.feature.shortcut

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import com.example.noties.MainActivity
import com.example.noties.R
import com.example.noties.common.navigation.Screen
import com.example.noties.common.utils.MY_ARG
import com.example.noties.common.utils.MY_URI
import com.example.noties.common.utils.ROUTE
import com.example.noties.feature.domain.model.Note
import com.example.noties.feature.domain.use_case.NoteUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ShortcutsHandler @Inject constructor(
    private val noteUseCase: NoteUseCase,
) {
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    suspend fun observeNotes(context: Context) {
        ShortcutManagerCompat.removeAllDynamicShortcuts(context)

        val shots = scope.async {
            noteUseCase.getNotesUseCase().map { notes ->
                notes.map { note ->
                    createShortcuts(context, note)
                }
            }.first()
        }

        val temp = shots.await()

        temp.take(3).forEach {
            ShortcutManagerCompat.pushDynamicShortcut(context, it)
        }


    }

}

fun createShortcuts(context: Context, note: Note): ShortcutInfoCompat {
    return ShortcutInfoCompat.Builder(context, "${note.id}")
        .setShortLabel("${note.title}")
        .setLongLabel("${note.title}")
        .setIcon(IconCompat.createWithResource(context, R.drawable.ic_alarm))
        .setIntent(
            Intent(
                Intent.ACTION_VIEW,
                "$MY_URI/$MY_ARG=${note.id}".toUri(),
                context, MainActivity::class.java
            ).apply {
                putExtra(ROUTE, Screen.EditScreen.route)
            }
        )
        .build()
}
