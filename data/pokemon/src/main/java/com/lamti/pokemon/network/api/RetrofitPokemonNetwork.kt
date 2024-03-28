package com.lamti.pokemon.network.api

import com.lamti.pokemon.network.model.list.NetworkPokemonList
import com.lamti.pokemon.network.model.pokemon.NetworkPokemon

class RetrofitPokemonNetwork(private val networkApi: RetrofitPokemonNetworkApi) : PokemonNetworkDataSource {

    override suspend fun getPokemonList(
        page: Int,
        pageSize: Int
    ): NetworkPokemonList = networkApi.getPokemonList(
        offset = page,
        limit = pageSize
    )

    override suspend fun getPokemon(code: Int): NetworkPokemon = networkApi.getPokemon(code)
}