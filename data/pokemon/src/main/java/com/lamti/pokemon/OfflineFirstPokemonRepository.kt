package com.lamti.pokemon

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.lamti.pokemon.database.asExternalModel
import com.lamti.pokemon.model.Pokemon
import com.lamti.pokemon.network.api.PokemonNetworkDataSource
import com.lamti.pokemon.network.model.pokemon.NetworkPokemon.Companion.toPokemon
import com.lamti.pokemon.paging.PokemonRemoteMediator
import com.lamti.pokemon.paging.PokemonRemoteMediator.Companion.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class OfflineFirstPokemonRepository(
    private val network: PokemonNetworkDataSource,
    private val database: com.lamti.pokemon.database.PokemonDatabase,
) : PokemonRepository {

    override fun getPokemonStream(code: Int): Flow<Pokemon> = flow {
        emit(network.getPokemon(code).toPokemon())
    }

    override fun getPokemonListStream(): Flow<PagingData<Pokemon>> {
        val pagingSourceFactory = { database.pokemonDao().getPokemonListStream() }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE,
                enablePlaceholders = true,
            ),
            remoteMediator = PokemonRemoteMediator(
                network = network,
                database = database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { pokemonEntity ->
                pokemonEntity.asExternalModel()
            }
        }
    }
}