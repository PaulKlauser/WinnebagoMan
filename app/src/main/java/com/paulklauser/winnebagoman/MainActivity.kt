package com.paulklauser.winnebagoman

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.mutableStateListOf
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
            val backingItems = remember {
                mutableStateListOf(
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
            }

            val displayItems = backingItems.map {
                soundItemHolder(
                    displayTextRes = it.displayTextRes,
                    audioAssetRes = it.audioAssetRes,
                    duration = it.duration,
                    itemPlaying = it.isPlaying
                )
            }

            MainScreen(items = displayItems, playAsset = remember {
                { item ->
                    mediaPlayers[item.audioAssetRes] =
                        MediaPlayer.create(this, item.audioAssetRes).apply {
                            val index =
                                backingItems.indexOfFirst { it.audioAssetRes == item.audioAssetRes }
                            backingItems[index] =
                                backingItems[index].copy(duration = duration, isPlaying = true)
                            setOnCompletionListener {
                                release()
                                backingItems[index] = backingItems[index].copy(isPlaying = false)
                                mediaPlayers.remove(item.audioAssetRes)
                            }
                            start()
                        }
                }
            })
        }
    }
}

@Composable
fun soundItemHolder(
    @StringRes displayTextRes: Int,
    @RawRes audioAssetRes: Int,
    duration: Int,
    itemPlaying: Boolean
): SoundItemHolder {
    return SoundItemHolder(
        displayTextRes = displayTextRes,
        audioAssetRes = audioAssetRes,
        progress = animateFloatAsState(
            targetValue = if (itemPlaying) 1f else 0f,
            animationSpec = if (itemPlaying) tween(
                duration,
                easing = LinearEasing
            ) else tween()
        ).value
    )
}

@Composable
fun MainScreen(
    items: List<SoundItemHolder>,
    playAsset: (SoundItemHolder) -> Unit
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
                    items(items) { item ->
                        SoundButton(soundItemHolder = item, playAsset = playAsset)
                    }
                }
            }
        }
    }
}

@Composable
fun SoundButton(
    modifier: Modifier = Modifier,
    soundItemHolder: SoundItemHolder,
    playAsset: (SoundItemHolder) -> Unit
) {
    Button(
        modifier = modifier
            .padding(vertical = 8.dp),
        onClick = { playAsset(soundItemHolder) },
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .defaultMinSize(minHeight = 70.dp)
                    .fillMaxWidth(soundItemHolder.progress)
                    .align(Alignment.CenterStart)
                    .background(Color.Cyan)
            )
            Text(
                text = stringResource(soundItemHolder.displayTextRes), modifier = Modifier
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
    MainScreen(items = listOf(), playAsset = {})
}