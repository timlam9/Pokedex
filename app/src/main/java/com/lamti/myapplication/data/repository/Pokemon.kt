package com.lamti.myapplication.data.repository

import com.lamti.myapplication.data.database.PokemonEntity

data class Pokemon(
    val name: String,
    val code: Int,
    val image: String,
    val type1: String,
    val type2: String?
)

fun Pokemon.asEntity() = PokemonEntity(
    id = code,
    name = name,
    imageUrl = image,
    type1 = type1,
    type2 = type2
)