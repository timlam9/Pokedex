package com.lamti.pokemonlist.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lamti.pokemon.PokemonRepository

class HomeViewModel(pokemonRepository: PokemonRepository) : ViewModel() {

    val pokemons = pokemonRepository.getPokemonListStream().cachedIn(viewModelScope)
}