package com.lamti.myapplication.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamti.myapplication.data.repository.PokemonRepository
import com.lamti.myapplication.data.Result
import com.lamti.myapplication.data.asResult
import com.lamti.myapplication.data.repository.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    val uiState: StateFlow<HomeUiState> = pokemonsUiStateStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

    private fun pokemonsUiStateStream(): Flow<HomeUiState> {
        return pokemonRepository.getPokemonListStream()
            .asResult()
            .map { result ->
                when (result) {
                    is Result.Error -> HomeUiState.Error(result.exception?.message.toString())
                    Result.Loading -> HomeUiState.Loading
                    is Result.Success -> HomeUiState.Success(result.data)
                }
            }
    }
}

sealed interface HomeUiState {
    data class Success(val pokemons: List<Pokemon>) : HomeUiState
    data class Error(val message: String) : HomeUiState
    object Loading : HomeUiState
}