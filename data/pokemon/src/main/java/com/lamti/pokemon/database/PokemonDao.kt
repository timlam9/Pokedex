package com.lamti.pokemon.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {

    @Query(value = "SELECT * FROM pokemon")
    fun getPokemonListStream(): PagingSource<Int, PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrIgnorePokemons(pokemonEntities: List<PokemonEntity>)

    @Query("DELETE FROM pokemon")
    suspend fun deleteAllPokemons()
}