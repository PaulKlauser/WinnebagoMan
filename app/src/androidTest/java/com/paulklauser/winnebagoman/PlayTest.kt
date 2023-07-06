package com.paulklauser.winnebagoman

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Not currently functional, requires updating to Compose 1.4.0+,
 * which incidentally breaks my animations atm.
 */
@RunWith(AndroidJUnit4::class)
class PlayTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun plays_asset() {
        var assetPlayed: Int? = null
        composeRule.setContent {
            MainScreen(
                items = listOf(
                    SoundItemHolder(
                        R.string.acutrama,
                        R.raw.acutrama,
                        .5f
                    )
                ),
                playAsset = {
                    assetPlayed = it.audioAssetRes
                }
            )
        }

        composeRule.onNodeWithText("Acutrama").performClick()

        assertEquals(R.raw.acutrama, assetPlayed)
    }
}