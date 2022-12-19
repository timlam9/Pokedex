package com.lamti.pokemon

import androidx.paging.PagingData
import com.lamti.pokemon.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonStream(code: Int): Flow<Pokemon>

    fun getPokemonListStream(): Flow<PagingData<Pokemon>>
}