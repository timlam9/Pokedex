package com.lamti.myapplication.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lamti.myapplication.data.repository.Pokemon

@Composable
fun PokemonGrid(pokemonList: List<Pokemon>, onPokemonClick: (code: String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
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