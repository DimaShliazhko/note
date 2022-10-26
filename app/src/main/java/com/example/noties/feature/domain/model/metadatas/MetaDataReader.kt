package com.example.noties.feature.domain.model.metadatas

import android.net.Uri

interface MetaDataReader {
    fun getMetadataFromUri(contentUri: Uri): MetaData?
}



