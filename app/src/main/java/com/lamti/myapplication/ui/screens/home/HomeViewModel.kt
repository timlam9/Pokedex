package com.lamti.myapplication.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lamti.myapplication.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    pokemonRepository: PokemonRepository
) : ViewModel() {

    val pokemons = pokemonRepository.getPokemonListStream().cachedIn(viewModelScope)
}