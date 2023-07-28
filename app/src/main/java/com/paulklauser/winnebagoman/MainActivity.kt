package com.paulklauser.winnebagoman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.paulklauser.winnebagoman.ui.theme.WinnebagoManTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            LaunchedEffect(Unit) {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = false
                )
            }

            val vm = viewModel<SoundBoardViewModel>()

            val displayItems = vm.backingItems.map {
                soundItemHolder(
                    displayTextRes = it.displayTextRes,
                    audioAssetRes = it.audioAssetRes,
                    duration = it.duration,
                    itemPlaying = it.isPlaying
                )
            }
            var isRetro by remember { mutableStateOf(true) }
            MainScreen(items = displayItems,
                playAsset = remember {
                    {
                        vm.playAsset(this, it.audioAssetRes, it.displayText)
                    }
                },
                isRetroTheme = isRetro,
                toggleTheme = remember { { isRetro = !isRetro } })
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
        displayText = stringResource(id = displayTextRes),
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
    playAsset: (SoundItemHolder) -> Unit,
    isRetroTheme: Boolean,
    toggleTheme: () -> Unit
) {
    WinnebagoManTheme(isRetroTheme = isRetroTheme) {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text(text = "Winnebago Man!!", color = Color.White) },
                contentPadding = WindowInsets.statusBars.asPaddingValues(),
                actions = {
                    Button(
                        onClick = toggleTheme,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        elevation = null
                    ) {
                        Image(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = "Toggle theme",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
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
                    .background(MaterialTheme.colors.secondary)
            )
            Text(
                text = soundItemHolder.displayText, modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RetroPreview() {
    MainScreen(
        items = listOf(
            SoundItemHolder(
                "Acutrama",
                R.raw.acutrama,
                .7f
            ),
            SoundItemHolder(
                "Something Else",
                R.raw.acutrama,
                0f
            )
        ), playAsset = {},
        isRetroTheme = true,
        toggleTheme = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ModernPreview() {
    MainScreen(
        items = listOf(
            SoundItemHolder(
                "Acutrama",
                R.raw.acutrama,
                .7f
            ),
            SoundItemHolder(
                "Something Else",
                R.raw.acutrama,
                0f
            )
        ), playAsset = {},
        isRetroTheme = false,
        toggleTheme = {}
    )
}