package com.paulklauser.winnebagoman

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SoundItem(
    @StringRes val displayTextRes: Int,
    @RawRes val audioAssetRes: Int,
    val progress: MutableState<Float> = mutableStateOf(0f)
)