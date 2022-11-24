package com.lamti.myapplication.data.network.api

import com.lamti.myapplication.data.network.model.list.NetworkPokemonList
import com.lamti.myapplication.data.network.model.pokemon.NetworkPokemon

interface PokemonNetworkDataSource {

    suspend fun getPokemonList(): NetworkPokemonList

    suspend fun getPokemon(code: Int): NetworkPokemon
}