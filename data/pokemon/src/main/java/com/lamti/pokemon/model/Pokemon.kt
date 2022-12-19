package com.lamti.pokemon.model

import com.lamti.pokemon.database.PokemonEntity
import com.lamti.pokemon.database.StatDB
import com.lamti.pokemon.database.StatXDB
import com.lamti.pokemon.network.model.pokemon.Stat


data class Pokemon(
    val name: String,
    val code: Int,
    val image: String,
    val type1: String,
    val type2: String?,
    val stats: List<Stat>,
    val color: PokemonColor
)

fun Pokemon.asEntity() = PokemonEntity(
    id = code,
    name = name,
    imageUrl = image,
    type1 = type1,
    type2 = type2,
    stats = if (stats.isNotEmpty()) stats.map { it.asDB() } else emptyList(),
    color = color.name
)

fun Stat.asDB() = StatDB(
    baseStat = baseStat,
    effort = effort,
    stat = StatXDB(name = stat.name, url = stat.url)
)