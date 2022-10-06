package com.example.noties.feature.domain.use_case

import android.content.Context
import com.example.noties.R
import java.io.File
import javax.inject.Inject

class GetOutputDirectoryUseCase @Inject constructor(
    private val context: Context
) {

    operator fun invoke(): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
    }

}