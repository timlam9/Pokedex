package com.lamti.pokemonlist.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.lamti.pokemonlist.R
import com.lamti.pokemonlist.components.common.PokemonError
import com.lamti.pokemonlist.components.common.PokemonLoader
import com.lamti.pokemonlist.components.home.PokemonGrid

@Composable
fun HomeRoute(
    onNavigateToDetails: (code: Int, color: Int) -> Unit,
    modifier: Modifier = Modifier,
    pokemons: LazyPagingItems<com.lamti.pokemon.model.Pokemon>,
) {
    HomeScreen(
        pokemons = pokemons,
        modifier = modifier,
        onPokemonClick = onNavigateToDetails
    )
}

@Composable
fun HomeScreen(
    pokemons: LazyPagingItems<com.lamti.pokemon.model.Pokemon>,
    modifier: Modifier,
    onPokemonClick: (code: Int, color: Int) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        when {
            pokemons.loadState.refresh is LoadState.Error && pokemons.itemCount <= 0 -> PokemonError()
            pokemons.itemCount <= 0 -> PokemonLoader(source = R.raw.squirtle, size = 300.dp)
            else -> {
                PokemonGrid(
                    pokemonList = pokemons,
                    onPokemonClick = onPokemonClick
                )
            }
        }
    }
}

