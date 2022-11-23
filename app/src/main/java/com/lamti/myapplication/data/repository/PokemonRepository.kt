package com.lamti.myapplication.data.repository

import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonStream(code: String): Flow<Pokemon>

    fun getPokemonListStream(): Flow<List<Pokemon>>
}