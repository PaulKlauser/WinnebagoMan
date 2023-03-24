package com.paulklauser.winnebagoman

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.paulklauser.winnebagoman.ui.theme.ToolbarGray
import com.paulklauser.winnebagoman.ui.theme.WinnebagoManTheme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainActivity : ComponentActivity() {

    private val mediaPlayers: MutableMap<Int, MediaPlayer> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            // Remember a SystemUiController
            val systemUiController = rememberSystemUiController()

            DisposableEffect(systemUiController) {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = false
                )

                onDispose {}
            }

            MainScreen(playAsset = remember {
                { item ->
                    mediaPlayers[item.audioAssetRes] =
                        MediaPlayer.create(this, item.audioAssetRes).apply {
                            val job = MainScope().launch {
                                checkProgress()
                            }
                            setOnCompletionListener {
                                release()
                                job.cancel()
                                item.progress.value = 0f
                                mediaPlayers.remove(item.audioAssetRes)
                            }
                            start()
                        }
                }
            })
        }
    }

    private suspend fun checkProgress() {
        if (!coroutineContext.isActive) {
            return
        }
        mediaPlayers.forEach { (resourceId, mediaPlayer) ->
            allSounds.forEach { soundItem ->
                if (soundItem.audioAssetRes == resourceId) {
                    soundItem.progress.value =
                        mediaPlayer.let { it.currentPosition.toFloat() / it.duration }
                }
            }
        }
        delay(10)
        checkProgress()
    }
}

val allSounds = listOf(
    SoundItem(R.string.acutrama, R.raw.acutrama),
    SoundItem(R.string.bathroom, R.raw.bathroom),
    SoundItem(R.string.kindness, R.raw.do_me_a_kindness),
    SoundItem(R.string.clue_here_now, R.raw.give_a_clue),
    SoundItem(R.string.dont_slam, R.raw.dont_slam_the_door),
    SoundItem(R.string.fall_off, R.raw.fall_off),
    SoundItem(R.string.fly_headlight, R.raw.fly_over_headlight),
    SoundItem(R.string.do_it_again, R.raw.do_it_again),
    SoundItem(R.string.you_believe, R.raw.believe),
    SoundItem(R.string.pth, R.raw.pth),
    SoundItem(R.string.the_word_rear, R.raw.rear),
    SoundItem(R.string.hehehe, R.raw.hahah),
    SoundItem(R.string.make_any_difference, R.raw.make_any_difference),
    SoundItem(R.string.dumbass, R.raw.you_dumbass),
    SoundItem(R.string.kick, R.raw.kick),
    SoundItem(R.string.my_mind, R.raw.mind_shit),
    SoundItem(R.string.no_more, R.raw.no_more),
    SoundItem(R.string.say_it_right, R.raw.say_it_right),
    SoundItem(R.string.shun_of_a, R.raw.shun_of_a_bitch),
    SoundItem(R.string.slate_this, R.raw.slate_this),
    SoundItem(R.string.what_did_i_say, R.raw.what_did_i_say),
    SoundItem(R.string.say_tony, R.raw.what_does_the_line_say_tony),
    SoundItem(R.string.flies, R.raw.god_damn_jackass),
    SoundItem(R.string.i_dont_give_a, R.raw.i_dont_give_a_shit),
    SoundItem(R.string.windshield, R.raw.windshield),
    SoundItem(R.string.god_dang_son_of_a, R.raw.god_damn_sonofa)
)

@Composable
fun MainScreen(
    playAsset: (SoundItem) -> Unit
) {
    WinnebagoManTheme {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text(text = "Winnebago Man!!", color = Color.White) },
                contentPadding = WindowInsets.statusBars.asPaddingValues(),
                backgroundColor = ToolbarGray
            )
        }) {
            Surface(
                modifier = Modifier.padding(paddingValues = it),
                color = MaterialTheme.colors.background
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(allSounds) { soundItem ->
                        SoundButton(soundItem = soundItem, playAsset = playAsset)
                    }
                }
            }
        }
    }
}

@Composable
fun SoundButton(
    modifier: Modifier = Modifier,
    soundItem: SoundItem,
    playAsset: (SoundItem) -> Unit
) {
    Button(
        modifier = modifier
            .padding(vertical = 8.dp),
        onClick = { playAsset(soundItem) },
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .defaultMinSize(minHeight = 70.dp)
                    .fillMaxWidth(soundItem.progress.value)
                    .align(Alignment.CenterStart)
                    .background(Color.Cyan)
            )
            Text(
                text = stringResource(soundItem.displayTextRes), modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen(playAsset = {})
}