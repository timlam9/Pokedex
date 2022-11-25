package com.lamti.myapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PokemonEntity::class, PokemonRemoteKeysEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    abstract fun pokemonRemoteKeysDao(): PokemonRemoteKeysDao
}