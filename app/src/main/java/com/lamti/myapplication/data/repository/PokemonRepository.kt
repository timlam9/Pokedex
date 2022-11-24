package com.lamti.myapplication.data.repository

import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonStream(code: Int): Flow<Pokemon>

    fun getPokemonListStream(): Flow<List<Pokemon>>
}