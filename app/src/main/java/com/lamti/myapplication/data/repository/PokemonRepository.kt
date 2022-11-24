package com.lamti.myapplication.data.repository

import com.lamti.myapplication.data.repository.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonStream(code: Int): Flow<Pokemon>

    fun getPokemonListStream(): Flow<List<Pokemon>>
}