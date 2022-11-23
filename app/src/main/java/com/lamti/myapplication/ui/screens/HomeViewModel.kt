package com.lamti.myapplication.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamti.myapplication.data.PokemonRepository
import com.lamti.myapplication.data.Result
import com.lamti.myapplication.data.asResult
import com.lamti.myapplication.ui.Pokemon
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
        return pokemonRepository.getPokemonsStream()
            .asResult()
            .map { result ->
                when (result) {
                    is Result.Error -> HomeUiState.Error
                    Result.Loading -> HomeUiState.Loading
                    is Result.Success -> HomeUiState.Success(result.data)
                }
            }
    }
}

sealed interface HomeUiState {
    data class Success(val pokemons: List<Pokemon>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}