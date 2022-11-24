package com.lamti.myapplication.data.repository

import com.lamti.myapplication.data.database.PokemonDao
import com.lamti.myapplication.data.database.asExternalModel
import com.lamti.myapplication.data.network.api.PokemonNetworkDataSource
import com.lamti.myapplication.data.network.model.list.NetworkPokemonList.Companion.toPokemons
import com.lamti.myapplication.data.network.model.pokemon.NetworkPokemon.Companion.toPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OfflineFirstPokemonRepository @Inject constructor(
    private val network: PokemonNetworkDataSource,
    private val pokemonDao: PokemonDao,
) : PokemonRepository {

    override fun getPokemonStream(code: Int): Flow<Pokemon> = flow {
        emit(network.getPokemon(code).toPokemon())
    }

    override fun getPokemonListStream(): Flow<List<Pokemon>> = flow {
        coroutineScope {
            val localPokemonList = pokemonDao.getPokemonListStream().first()
            val pokemons = if (localPokemonList.isEmpty()) {
                network.getPokemonList()
                    .toPokemons()
                    .map { async { network.getPokemon(it.code) } }
                    .awaitAll()
                    .map { it.toPokemon() }
                    .also { networkList ->
                        pokemonDao.insertOrIgnorePokemons(networkList.map { it.asEntity() })
                    }
            } else
                localPokemonList.map {
                    it.asExternalModel()
                }
            emit(pokemons)
        }
    }.flowOn(Dispatchers.IO)
}