package com.paulklauser.winnebagoman

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class SoundBoardViewModel : ViewModel() {

    private val mediaPlayers: MutableMap<Int, MediaPlayer> = mutableMapOf()
    val backingItems = mutableStateListOf(
        BackingSoundItem(R.string.acutrama, R.raw.acutrama),
        BackingSoundItem(R.string.bathroom, R.raw.bathroom),
        BackingSoundItem(R.string.kindness, R.raw.do_me_a_kindness),
        BackingSoundItem(R.string.clue_here_now, R.raw.give_a_clue),
        BackingSoundItem(R.string.dont_slam, R.raw.dont_slam_the_door),
        BackingSoundItem(R.string.fall_off, R.raw.fall_off),
        BackingSoundItem(R.string.fly_headlight, R.raw.fly_over_headlight),
        BackingSoundItem(R.string.do_it_again, R.raw.do_it_again),
        BackingSoundItem(R.string.you_believe, R.raw.believe),
        BackingSoundItem(R.string.pth, R.raw.pth),
        BackingSoundItem(R.string.the_word_rear, R.raw.rear),
        BackingSoundItem(R.string.hehehe, R.raw.hahah),
        BackingSoundItem(R.string.make_any_difference, R.raw.make_any_difference),
        BackingSoundItem(R.string.dumbass, R.raw.you_dumbass),
        BackingSoundItem(R.string.kick, R.raw.kick),
        BackingSoundItem(R.string.my_mind, R.raw.mind_shit),
        BackingSoundItem(R.string.no_more, R.raw.no_more),
        BackingSoundItem(R.string.say_it_right, R.raw.say_it_right),
        BackingSoundItem(R.string.shun_of_a, R.raw.shun_of_a_bitch),
        BackingSoundItem(R.string.slate_this, R.raw.slate_this),
        BackingSoundItem(R.string.what_did_i_say, R.raw.what_did_i_say),
        BackingSoundItem(R.string.say_tony, R.raw.what_does_the_line_say_tony),
        BackingSoundItem(R.string.flies, R.raw.god_damn_jackass),
        BackingSoundItem(R.string.i_dont_give_a, R.raw.i_dont_give_a_shit),
        BackingSoundItem(R.string.windshield, R.raw.windshield),
        BackingSoundItem(R.string.god_dang_son_of_a, R.raw.god_damn_sonofa)
    )

    fun playAsset(context: Context, @RawRes assetRes: Int) {
        mediaPlayers[assetRes] =
            MediaPlayer.create(context, assetRes).apply {
                val index =
                    backingItems.indexOfFirst { it.audioAssetRes == assetRes }
                backingItems[index] =
                    backingItems[index].copy(duration = duration, isPlaying = true)
                setOnCompletionListener {
                    release()
                    backingItems[index] = backingItems[index].copy(isPlaying = false)
                    mediaPlayers.remove(assetRes)
                }
                start()
            }
    }

}