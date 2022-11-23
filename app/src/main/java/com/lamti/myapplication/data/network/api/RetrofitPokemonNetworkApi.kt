package com.lamti.myapplication.data.network.api

import com.lamti.myapplication.data.network.model.list.NetworkPokemonList
import com.lamti.myapplication.data.network.model.pokemon.NetworkPokemon
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RetrofitPokemonNetworkApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20
    ): NetworkPokemonList

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Query("id") code: String): NetworkPokemon
}