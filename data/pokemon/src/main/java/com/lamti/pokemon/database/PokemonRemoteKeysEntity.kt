package com.lamti.pokemon.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_remote_keys")
data class PokemonRemoteKeysEntity(
    @PrimaryKey
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)