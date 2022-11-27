package com.lamti.myapplication.ui.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lamti.myapplication.R

@Composable
fun PokemonError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LoadingAnimation(resource = R.raw.connection_lost, size = 300.dp)
        Text(
            text = "Something went wrong.\nCheck your internet connection!",
            style = MaterialTheme.typography.h6.copy(
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}