package com.lamti.myapplication.data.network.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lamti.myapplication.data.network.model.list.NetworkPokemonList
import com.lamti.myapplication.data.network.model.pokemon.NetworkPokemon
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

const val PokemonBaseUrl = "https://pokeapi.co/api/v2/"

@Singleton
class RetrofitPokemonNetwork @Inject constructor(networkJson: Json) : PokemonNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(PokemonBaseUrl)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
                .build()
        )
        .addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(RetrofitPokemonNetworkApi::class.java)

    override suspend fun getPokemonList(): NetworkPokemonList = networkApi.getPokemonList()

    override suspend fun getPokemon(code: Int): NetworkPokemon = networkApi.getPokemon(code)
}