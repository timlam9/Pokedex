package com.lamti.pokemon.network.api

import com.lamti.pokemon.network.model.list.NetworkPokemonList
import com.lamti.pokemon.network.model.pokemon.NetworkPokemon

interface PokemonNetworkDataSource {

    suspend fun getPokemonList(page: Int, pageSize: Int): NetworkPokemonList

    suspend fun getPokemon(code: Int): NetworkPokemon
}