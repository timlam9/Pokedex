package com.lamti.pokemon.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lamti.pokemon.model.asEntity
import com.lamti.pokemon.network.api.PokemonNetworkDataSource
import com.lamti.pokemon.network.model.list.NetworkPokemonList
import com.lamti.pokemon.network.model.list.NetworkPokemonList.Companion.toPokemons
import com.lamti.pokemon.network.model.pokemon.NetworkPokemon.Companion.toPokemon
import kotlinx.coroutines.*
import javax.inject.Inject

class PokemonRemoteMediator @Inject constructor(
    private val network: PokemonNetworkDataSource,
    private val database: com.lamti.pokemon.database.PokemonDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemoteMediator<Int, com.lamti.pokemon.database.PokemonEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, com.lamti.pokemon.database.PokemonEntity>
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

            val pokemons: List<com.lamti.pokemon.database.PokemonEntity> = withContext(dispatcher) {
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
                    com.lamti.pokemon.database.PokemonRemoteKeysEntity(
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
        state: PagingState<Int, com.lamti.pokemon.database.PokemonEntity>
    ): com.lamti.pokemon.database.PokemonRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.pokemonRemoteKeysDao().getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, com.lamti.pokemon.database.PokemonEntity>
    ): com.lamti.pokemon.database.PokemonRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokemonEntity ->
                database.pokemonRemoteKeysDao().getRemoteKeys(id = pokemonEntity.id.toString())
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, com.lamti.pokemon.database.PokemonEntity>
    ): com.lamti.pokemon.database.PokemonRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokemonEntity ->
                database.pokemonRemoteKeysDao().getRemoteKeys(id = pokemonEntity.id.toString())
            }
    }

    companion object {

        const val PAGE_SIZE = 20
    }
}