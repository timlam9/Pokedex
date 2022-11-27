package com.lamti.myapplication.ui.components.common

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    resource: Int,
    isPlaying: Boolean = true,
    animationSpeed: Float = 1f,
    restartOnPlay: Boolean = false,
    iterations: Int = LottieConstants.IterateForever,
    size: Dp = 300.dp,
    rotation: Float = 0f,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resource))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterations,
        isPlaying = isPlaying,
        speed = animationSpeed,
        restartOnPlay = restartOnPlay,
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
            .size(size)
            .rotate(rotation)
    )
}