package com.lamti.myapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query(value = "SELECT * FROM pokemon")
    fun getPokemonListStream(): Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrIgnorePokemons(pokemonEntities: List<PokemonEntity>)
}