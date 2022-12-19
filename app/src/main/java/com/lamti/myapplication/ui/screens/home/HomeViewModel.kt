package com.lamti.myapplication.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    pokemonRepository: com.lamti.pokemon.PokemonRepository
) : ViewModel() {

    val pokemons = pokemonRepository.getPokemonListStream().cachedIn(viewModelScope)
}