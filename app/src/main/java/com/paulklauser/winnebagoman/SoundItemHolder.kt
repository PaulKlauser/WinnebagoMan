package com.paulklauser.winnebagoman

import androidx.annotation.RawRes

data class SoundItemHolder(
    val displayText: String,
    @RawRes val audioAssetRes: Int,
    val progress: Float = 0f
)