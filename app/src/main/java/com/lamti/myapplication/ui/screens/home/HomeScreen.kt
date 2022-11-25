package com.lamti.myapplication.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.lamti.myapplication.data.repository.model.Pokemon
import com.lamti.myapplication.ui.components.common.PokemonLoader
import com.lamti.myapplication.ui.components.home.PokemonList

@Composable
internal fun HomeRoute(
    onNavigateToDetails: (code: Int, color: Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val pokemons = viewModel.pokemons.collectAsLazyPagingItems()

    HomeScreen(
        pokemons = pokemons,
        modifier = modifier,
        onPokemonClick = onNavigateToDetails
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState? = null,
    modifier: Modifier,
    onPokemonClick: (code: Int, color: Int) -> Unit,
    pokemons: LazyPagingItems<Pokemon>,
) {
    when (uiState) {
        is HomeUiState.Error -> {
            println(uiState.message)
        }
        HomeUiState.Loading -> PokemonLoader()
        is HomeUiState.Success -> {}
        else -> {
            PokemonList(
                pokemons = pokemons,
                modifier = modifier,
                onPokemonClick = onPokemonClick
            )
        }
    }
}

