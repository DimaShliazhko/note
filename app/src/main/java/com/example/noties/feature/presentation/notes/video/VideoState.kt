package com.example.noties.feature.presentation.notes.video

import com.example.noties.common.base.State
import com.example.noties.feature.domain.model.VideoItem

data class VideoState(
    val isLoading: Boolean = false,
    val videoItems: List<VideoItem>
) : State