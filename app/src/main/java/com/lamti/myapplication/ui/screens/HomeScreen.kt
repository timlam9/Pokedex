package com.lamti.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lamti.myapplication.data.repository.model.Pokemon
import com.lamti.myapplication.ui.components.PokemonGrid
import com.lamti.myapplication.ui.components.PokemonLoader

@Composable
internal fun HomeRoute(
    onNavigateToDetails: (code: Int, color: Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState: HomeUiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        modifier = modifier,
        onPokemonClick = onNavigateToDetails
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier,
    onPokemonClick: (code: Int, color: Int) -> Unit,
) {
    when (uiState) {
        is HomeUiState.Error -> {
            println(uiState.message)
        }
        HomeUiState.Loading -> PokemonLoader()
        is HomeUiState.Success -> PokemonList(
            pokemons = uiState.pokemons,
            modifier = modifier,
            onPokemonClick = onPokemonClick
        )
    }
}

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    pokemons: List<Pokemon>,
    onPokemonClick: (code: Int, color: Int) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        PokemonGrid(
            pokemonList = pokemons,
            onPokemonClick = onPokemonClick
        )
    }
}
