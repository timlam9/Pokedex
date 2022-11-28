package com.lamti.myapplication.data.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lamti.myapplication.data.database.PokemonDatabase
import com.lamti.myapplication.data.database.PokemonEntity
import com.lamti.myapplication.data.database.PokemonRemoteKeysEntity
import com.lamti.myapplication.data.network.api.PokemonNetworkDataSource
import com.lamti.myapplication.data.network.model.list.NetworkPokemonList
import com.lamti.myapplication.data.network.model.list.NetworkPokemonList.Companion.toPokemons
import com.lamti.myapplication.data.network.model.pokemon.NetworkPokemon.Companion.toPokemon
import com.lamti.myapplication.data.repository.model.asEntity
import kotlinx.coroutines.*
import javax.inject.Inject

class PokemonRemoteMediator @Inject constructor(
    private val network: PokemonNetworkDataSource,
    private val database: PokemonDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemoteMediator<Int, PokemonEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }

            val pokemons: List<PokemonEntity> = withContext(dispatcher) {
                val response: NetworkPokemonList = network.getPokemonList(
                    page = (currentPage - 1) * PAGE_SIZE,
                    pageSize = PAGE_SIZE
                )
                response.toPokemons()
                    .map { async { network.getPokemon(it.code) } }
                    .awaitAll()
                    .map { it.toPokemon() }
                    .map { it.asEntity() }
            }

            val endOfPaginationReached = pokemons.isEmpty()
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.pokemonDao().deleteAllPokemons()
                    database.pokemonRemoteKeysDao().deleteAllRemoteKeys()
                }
                val keys = pokemons.map {
                    PokemonRemoteKeysEntity(
                        id = it.id.toString(),
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                database.pokemonRemoteKeysDao().addAllRemoteKeys(remoteKeys = keys)
                database.pokemonDao().insertOrIgnorePokemons(pokemons)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PokemonEntity>
    ): PokemonRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.pokemonRemoteKeysDao().getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PokemonEntity>
    ): PokemonRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokemonEntity ->
                database.pokemonRemoteKeysDao().getRemoteKeys(id = pokemonEntity.id.toString())
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PokemonEntity>
    ): PokemonRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokemonEntity ->
                database.pokemonRemoteKeysDao().getRemoteKeys(id = pokemonEntity.id.toString())
            }
    }

    companion object {

        const val PAGE_SIZE = 20
    }
}