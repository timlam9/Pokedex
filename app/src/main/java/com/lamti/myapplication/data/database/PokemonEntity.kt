package com.lamti.myapplication.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lamti.myapplication.data.repository.model.Pokemon

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(defaultValue = "")
    val type1: String,
    val type2: String?
)

fun PokemonEntity.asExternalModel() = Pokemon(
    name = name,
    code = id,
    image = imageUrl,
    type1 = type1,
    type2 = type2,
    stats = emptyList()
)