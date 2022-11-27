package com.lamti.myapplication.ui.components.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.lamti.myapplication.data.repository.model.Pokemon

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    pokemons: LazyPagingItems<Pokemon>,
    onPokemonClick: (code: Int, color: Int) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        PokemonGrid(
            pokemonList = pokemons,
            onPokemonClick = onPokemonClick
        )
    }
}