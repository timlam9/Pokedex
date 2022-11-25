package com.lamti.myapplication.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.lamti.myapplication.data.repository.PokemonRepository
import com.lamti.myapplication.data.repository.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    pokemonRepository: PokemonRepository
) : ViewModel() {

    val pokemons = pokemonRepository.getPokemonListStream()
}

sealed interface HomeUiState {
    data class Success(val pokemons: Flow<PagingData<Pokemon>>) : HomeUiState
    data class Error(val message: String) : HomeUiState
    object Loading : HomeUiState
}