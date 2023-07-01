package com.paulklauser.winnebagoman

import androidx.annotation.RawRes
import androidx.annotation.StringRes

data class BackingSoundItem(
    @StringRes val displayTextRes: Int,
    @RawRes val audioAssetRes: Int,
    val duration: Int = 0,
    val isPlaying: Boolean = false
)