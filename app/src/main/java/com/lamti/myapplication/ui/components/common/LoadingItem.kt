package com.lamti.myapplication.ui.components.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lamti.myapplication.R

@Composable
fun LoadingItem(modifier: Modifier = Modifier, rotation: Float = 0f) {
    Box(
        modifier = modifier.size(160.dp),
        contentAlignment = Alignment.Center
    ) {
        LoadingAnimation(
            resource = R.raw.pokeball,
            modifier = Modifier.size(100.dp),
            rotation = rotation
        )
    }
}