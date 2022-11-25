package com.lamti.myapplication.ui.components.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lamti.myapplication.data.repository.model.Pokemon

@Composable
fun BottomSheetContent(pokemon: Pokemon) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(
                top = 50.dp,
                start = 20.dp,
                end = 20.dp
            )
    ) {
        PokemonBaseStats(pokemon = pokemon)
    }
}