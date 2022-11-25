package com.lamti.myapplication.ui.screens.details

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamti.myapplication.data.Result
import com.lamti.myapplication.data.asResult
import com.lamti.myapplication.data.repository.PokemonRepository
import com.lamti.myapplication.data.repository.model.Pokemon
import com.lamti.myapplication.ui.navigation.DetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    private val detailsArgs = DetailsArgs(savedStateHandle)

    val uiState: StateFlow<DetailsUiState> = pokemonUiStateStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailsUiState.Loading(Color(detailsArgs.color.toInt()))
        )

    private fun pokemonUiStateStream(): Flow<DetailsUiState> {
        return pokemonRepository.getPokemonStream(detailsArgs.code.toInt())
            .asResult()
            .map { result ->
                when (result) {
                    is Result.Error -> DetailsUiState.Error
                    Result.Loading -> DetailsUiState.Loading(Color(detailsArgs.color.toInt()))
                    is Result.Success -> DetailsUiState.Success(
                        pokemon = result.data,
                        dominantColor = Color(detailsArgs.color.toInt())
                    )
                }
            }
    }
}

sealed interface DetailsUiState {
    data class Success(val pokemon: Pokemon, val dominantColor: Color) : DetailsUiState
    object Error : DetailsUiState
    data class Loading(val dominantColor: Color) : DetailsUiState
}