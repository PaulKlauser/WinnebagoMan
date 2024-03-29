package com.paulklauser.winnebagoman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.paulklauser.winnebagoman.ui.theme.LightBlue
import com.paulklauser.winnebagoman.ui.theme.LightGreen
import com.paulklauser.winnebagoman.ui.theme.LightOrange
import com.paulklauser.winnebagoman.ui.theme.LightPink
import com.paulklauser.winnebagoman.ui.theme.LightPurple
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
                toggleTheme = remember { { isRetro = it } })
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

private val potentialButtonColors = listOf(
    LightBlue,
    LightGreen,
    LightOrange,
    LightPink,
    LightPurple
)

@Composable
fun MainScreen(
    items: List<SoundItemHolder>,
    playAsset: (SoundItemHolder) -> Unit,
    isRetroTheme: Boolean,
    toggleTheme: (Boolean) -> Unit
) {
    WinnebagoManTheme(isRetroTheme = isRetroTheme) {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text(text = "Winnebago Man!!", color = Color.White) },
                contentPadding = WindowInsets.statusBars.asPaddingValues(),
                actions = {
                    Text(text = "Retro Theme")
                    Checkbox(checked = isRetroTheme, onCheckedChange = toggleTheme)
                }
            )
        }) {
            Surface(
                modifier = Modifier.padding(paddingValues = it),
                color = MaterialTheme.colors.background
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = 8.dp,
                        top = 8.dp,
                        end = 8.dp,
                        bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(items) { index, item ->
                        SoundButton(
                            soundItemHolder = item,
                            playAsset = playAsset,
                            backgroundColor = if (isRetroTheme) MaterialTheme.colors.primary else potentialButtonColors[index % 5]
                        )
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
    playAsset: (SoundItemHolder) -> Unit,
    backgroundColor: Color = MaterialTheme.colors.primary
) {
    val scale = remember { Animatable(1f) }
    val isPlaying = soundItemHolder.progress > 0
    val springSpec = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
    LaunchedEffect(key1 = isPlaying) {
        if (isPlaying) {
            scale.animateTo(1.25f, animationSpec = springSpec)
        } else {
            scale.animateTo(1f, animationSpec = springSpec)
        }
    }
    Button(
        modifier = modifier
            .padding(vertical = 8.dp)
            .scale(scale.value)
            .zIndex(if (scale.value > 1f) 1f else 0f),
        onClick = { playAsset(soundItemHolder) },
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            backgroundColor = backgroundColor
        ),
        elevation = if (soundItemHolder.progress > 0) ButtonDefaults.elevation(defaultElevation = 20.dp) else ButtonDefaults.elevation(
            defaultElevation = 0.dp
        )
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
            ),
            SoundItemHolder(
                "Acutrama",
                R.raw.acutrama,
                0f
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