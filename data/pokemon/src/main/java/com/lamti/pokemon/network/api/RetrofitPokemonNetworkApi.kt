package com.lamti.pokemon.network.api

import com.lamti.pokemon.network.model.list.NetworkPokemonList
import com.lamti.pokemon.network.model.pokemon.NetworkPokemon
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface RetrofitPokemonNetworkApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): NetworkPokemonList

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): NetworkPokemon
}