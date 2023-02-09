package com.paulklauser.winnebagoman

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.paulklauser.winnebagoman.ui.theme.ToolbarGray
import com.paulklauser.winnebagoman.ui.theme.WinnebagoManTheme

class MainActivity : ComponentActivity() {
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

            MainScreen(playAsset = {
                MediaPlayer.create(this, it).apply {
                    setOnCompletionListener { release() }
                    start()
                }
            })
        }
    }
}

@Composable
fun MainScreen(playAsset: (Int) -> Unit) {
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
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Acutrama",
                            assetRes = R.raw.acutrama,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Bathroom",
                            assetRes = R.raw.bathroom,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Kindness",
                            assetRes = R.raw.do_me_a_kindness,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Clue Here Now",
                            assetRes = R.raw.give_a_clue,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Don't Slam",
                            assetRes = R.raw.dont_slam_the_door,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Fall Off",
                            assetRes = R.raw.fall_off,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Fly Headlight",
                            assetRes = R.raw.fly_over_headlight,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Do It Again",
                            assetRes = R.raw.do_it_again,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "You Believe That?",
                            assetRes = R.raw.believe,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = ":P",
                            assetRes = R.raw.pth,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "The Word Rear",
                            assetRes = R.raw.rear,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Hehehe",
                            assetRes = R.raw.hahah,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Make Any Difference",
                            assetRes = R.raw.make_any_difference,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Dumbass",
                            assetRes = R.raw.you_dumbass,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Kick",
                            assetRes = R.raw.kick,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "My Mind",
                            assetRes = R.raw.mind_shit,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "No More",
                            assetRes = R.raw.no_more,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Say It Right",
                            assetRes = R.raw.say_it_right,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Shun Of A...",
                            assetRes = R.raw.shun_of_a_bitch,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Slate This",
                            assetRes = R.raw.slate_this,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "What Did I Say?",
                            assetRes = R.raw.what_did_i_say,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Say Tony?",
                            assetRes = R.raw.what_does_the_line_say_tony,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Flies",
                            assetRes = R.raw.god_damn_jackass,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "I Don't Give A...",
                            assetRes = R.raw.i_dont_give_a_shit,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "Windshield",
                            assetRes = R.raw.windshield,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SoundButton(
                            modifier = Modifier.weight(1f),
                            title = "God Dang Son Of A...",
                            assetRes = R.raw.god_damn_sonofa,
                            playAsset = playAsset
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Spacer(modifier = Modifier.padding(WindowInsets.navigationBars.asPaddingValues()))
                }
            }
        }
    }
}

@Composable
fun SoundButton(
    modifier: Modifier = Modifier,
    title: String,
    assetRes: Int,
    playAsset: (Int) -> Unit
) {
    Button(modifier = modifier
        .padding(vertical = 8.dp)
        .defaultMinSize(minHeight = 70.dp),
        onClick = { playAsset(assetRes) }) {
        Text(text = title, modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen(playAsset = {})
}