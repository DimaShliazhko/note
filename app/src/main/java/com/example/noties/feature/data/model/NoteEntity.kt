package com.example.noties.feature.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "uri")
    val uri: String,
    @ColumnInfo(name = "add_time")
    val addTime: Long?,
    @ColumnInfo(name = "color")
    val color: Int,
    @ColumnInfo(name = "notification_time")
    val notificationTime: Long?
) {
}