package com.lamti.myapplication.data.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lamti.myapplication.data.database.*
import com.lamti.myapplication.data.network.api.PokemonNetworkDataSource
import com.lamti.myapplication.data.network.api.RetrofitPokemonNetwork
import com.lamti.myapplication.data.network.model.list.NetworkPokemonList
import com.lamti.myapplication.data.network.model.list.NetworkPokemonList.Companion.toPokemons
import com.lamti.myapplication.data.network.model.pokemon.NetworkPokemon.Companion.toPokemon
import com.lamti.myapplication.data.repository.model.asEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRemoteMediator @Inject constructor(
    private val pokemonApi: RetrofitPokemonNetwork,
    private val network: PokemonNetworkDataSource,
    private val database: PokemonDatabase,
    private val pokemonDao: PokemonDao,
    private val pokemonRemoteKeysDao: PokemonRemoteKeysDao,
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

            val pokemons: List<PokemonEntity> = withContext(Dispatchers.IO) {
                val response: NetworkPokemonList = pokemonApi.getPokemonList(
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
                    pokemonDao.deleteAllPokemons()
                    pokemonRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = pokemons.map {
                    PokemonRemoteKeysEntity(
                        id = it.id.toString(),
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                pokemonRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                pokemonDao.insertOrIgnorePokemons(pokemons)
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
                pokemonRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PokemonEntity>
    ): PokemonRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokemonEntity ->
                pokemonRemoteKeysDao.getRemoteKeys(id = pokemonEntity.id.toString())
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PokemonEntity>
    ): PokemonRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokemonEntity ->
                pokemonRemoteKeysDao.getRemoteKeys(id = pokemonEntity.id.toString())
            }
    }

    companion object {

        const val PAGE_SIZE = 10
    }
}