package com.lamti.myapplication.ui.components.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lamti.myapplication.R

@Composable
fun PokemonLoader(
    modifier: Modifier = Modifier,
    source: Int = R.raw.pokeball,
    size: Dp = 300.dp,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadingAnimation(
            resource = source,
            size = size
        )
    }
}