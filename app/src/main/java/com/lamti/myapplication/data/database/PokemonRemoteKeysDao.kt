package com.lamti.myapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonRemoteKeysDao {

    @Query("SELECT * FROM pokemon_remote_keys WHERE id =:id")
    suspend fun getRemoteKeys(id: String): PokemonRemoteKeysEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllRemoteKeys(remoteKeys: List<PokemonRemoteKeysEntity>)

    @Query("DELETE FROM pokemon_remote_keys")
    suspend fun deleteAllRemoteKeys()
}