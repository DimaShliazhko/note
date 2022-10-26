package com.example.noties.feature.domain.model.metadatas

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

class MetaDataReaderImpl (
    private val appContext: Context
) : MetaDataReader {
    override fun getMetadataFromUri(contentUri: Uri): MetaData? {
        if (contentUri.scheme != "content") {
            return null
        }
        val fileName = appContext.contentResolver.query(
            contentUri, arrayOf(
                MediaStore.Video.VideoColumns
                    .DISPLAY_NAME
            ), null, null, null
        )?.use { cursor ->
            val index = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            cursor.getString(index)
        }
        return fileName?.let { fullName ->
            MetaData(fileName = Uri.parse(fullName).lastPathSegment)
        }
    }
}
