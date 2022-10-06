package com.example.noties.feature.domain.model

import android.net.Uri
import android.os.Parcelable
import androidx.core.net.toUri
import com.example.noties.feature.data.model.NoteEntity
import com.example.noties.ui.theme.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
    val id: Long? = null,
    val title: String,
    val content: String,
    val color: Int,
    val addTime: Long? = null,
    var notificationTime: Long? = null,
    var uri: Uri? = null
) : Parcelable {

    companion object {
        val listColors = listOf(RedOrange, RedPink, BabyBlue, Violet, LightGreen)
    }
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        addTime = this.addTime,
        notificationTime = this.notificationTime,
        color = this.color,
        uri = this.uri.toString()
    )
}

fun NoteEntity.toNote(): Note {
    return Note(
        id = this.id,
        title = this.title,
        content = this.content,
        addTime = this.addTime,
        notificationTime = this.notificationTime,
        color = this.color,
        uri = this.uri.toUri()
    )
}