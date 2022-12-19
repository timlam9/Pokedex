package com.lamti.pokemon.network.model.list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
) {

    companion object {

        fun Result.toPokemon(): com.lamti.pokemon.model.Pokemon = com.lamti.pokemon.model.Pokemon(
            name = name,
            code = getCode(),
            image = "",
            type1 = "",
            type2 = null,
            stats = emptyList(),
            color = com.lamti.pokemon.model.PokemonColor.Ghost
        )

        private fun Result.getCode(): Int = url.split("/".toRegex()).dropLast(1).last().toInt()
    }
}