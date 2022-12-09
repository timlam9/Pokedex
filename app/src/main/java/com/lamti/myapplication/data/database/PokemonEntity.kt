package com.lamti.myapplication.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lamti.myapplication.data.network.model.pokemon.Stat
import com.lamti.myapplication.data.network.model.pokemon.StatX
import com.lamti.myapplication.data.network.model.pokemon.toPokemonColor
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
    val type2: String?,
    val stats: List<StatDB>,
    val color: String
)

data class StatDB(
    val baseStat: Int,
    val effort: Int,
    val stat: StatXDB
)

data class StatXDB(
    val name: String,
    val url: String
)

fun PokemonEntity.asExternalModel() = Pokemon(
    name = name,
    code = id,
    image = imageUrl,
    type1 = type1,
    type2 = type2,
    stats = stats.map { it.asExternalModel() },
    color = color.toPokemonColor()
)

fun StatDB.asExternalModel() = Stat(
    baseStat = baseStat,
    effort = effort,
    stat = stat.asExternalModel()
)

fun StatXDB.asExternalModel() = StatX(name = name, url = url)