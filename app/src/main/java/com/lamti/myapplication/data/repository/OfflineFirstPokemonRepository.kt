package com.lamti.myapplication.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.lamti.myapplication.data.database.PokemonDatabase
import com.lamti.myapplication.data.database.asExternalModel
import com.lamti.myapplication.data.network.api.PokemonNetworkDataSource
import com.lamti.myapplication.data.network.model.pokemon.NetworkPokemon.Companion.toPokemon
import com.lamti.myapplication.data.paging.PokemonRemoteMediator
import com.lamti.myapplication.data.paging.PokemonRemoteMediator.Companion.PAGE_SIZE
import com.lamti.myapplication.data.repository.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstPokemonRepository @Inject constructor(
    private val network: PokemonNetworkDataSource,
    private val database: PokemonDatabase,
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