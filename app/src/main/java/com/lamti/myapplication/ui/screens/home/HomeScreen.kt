package com.lamti.myapplication.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.lamti.myapplication.R
import com.lamti.myapplication.data.repository.model.Pokemon
import com.lamti.myapplication.ui.components.common.PokemonError
import com.lamti.myapplication.ui.components.common.PokemonLoader
import com.lamti.myapplication.ui.components.home.PokemonGrid

@Composable
internal fun HomeRoute(
    onNavigateToDetails: (code: Int, color: Int) -> Unit,
    modifier: Modifier = Modifier,
    pokemons: LazyPagingItems<Pokemon>,
) {
    HomeScreen(
        pokemons = pokemons,
        modifier = modifier,
        onPokemonClick = onNavigateToDetails
    )
}

@Composable
fun HomeScreen(
    pokemons: LazyPagingItems<Pokemon>,
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

