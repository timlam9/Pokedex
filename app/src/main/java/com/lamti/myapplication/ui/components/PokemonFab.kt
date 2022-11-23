package com.lamti.myapplication.ui.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable

@Composable
fun PokemonFAB(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Favorite"
        )
    }
}