package com.paulklauser.winnebagoman

import androidx.annotation.RawRes
import androidx.annotation.StringRes

data class SoundItemHolder(
    @StringRes val displayTextRes: Int,
    @RawRes val audioAssetRes: Int,
    val progress: Float = 0f
)