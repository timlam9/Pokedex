package com.lamti.myapplication.data.repository

import com.lamti.myapplication.data.network.api.PokemonNetworkDataSource
import com.lamti.myapplication.data.network.model.list.NetworkPokemonList.Companion.toPokemons
import com.lamti.myapplication.data.network.model.pokemon.NetworkPokemon.Companion.toPokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OfflineFirstPokemonRepository @Inject constructor(
    private val network: PokemonNetworkDataSource
) : PokemonRepository {

    override fun getPokemonStream(code: String): Flow<Pokemon> = flow {
        emit(network.getPokemon(code).toPokemon())
    }

    override fun getPokemonListStream(): Flow<List<Pokemon>> = flow {
        emit(network.getPokemonList().toPokemons())
    }
}