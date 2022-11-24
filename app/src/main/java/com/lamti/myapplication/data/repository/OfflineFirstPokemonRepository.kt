package com.lamti.myapplication.data.repository

import com.lamti.myapplication.data.network.api.PokemonNetworkDataSource
import com.lamti.myapplication.data.network.model.list.NetworkPokemonList.Companion.toPokemons
import com.lamti.myapplication.data.network.model.pokemon.NetworkPokemon.Companion.toPokemon
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OfflineFirstPokemonRepository @Inject constructor(
    private val network: PokemonNetworkDataSource,
) : PokemonRepository {

    override fun getPokemonStream(code: Int): Flow<Pokemon> = flow {
        emit(network.getPokemon(code).toPokemon())
    }

    override fun getPokemonListStream(): Flow<List<Pokemon>> = flow {
        coroutineScope {
            emit(
                network.getPokemonList()
                    .toPokemons()
                    .map { async { network.getPokemon(it.code) } }
                    .awaitAll()
                    .map { it.toPokemon() }
            )
        }
    }
}