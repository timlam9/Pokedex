package com.lamti.myapplication.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lamti.myapplication.ui.Pokemon

@Composable
fun PokemonGrid(pokemonList: List<Pokemon>, onPokemonClick: (code: String) -> Unit) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(pokemonList) {
            PokemonCard(
                modifier = Modifier.padding(4.dp),
                name = it.name,
                code = it.code,
                image = it.image,
                type1 = it.type1,
                type2 = it.type2,
                onClick = { onPokemonClick(it.code) }
            )
        }
    }
}