package com.lamti.myapplication.data.network.model.list

import com.lamti.myapplication.data.repository.Pokemon
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

        private const val ImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

        fun Result.toPokemon(): Pokemon = Pokemon(
            name = name,
            code = getCode(),
            image = getImageUrl(),
            type1 = "",
            type2 = null
        )

        private fun Result.getCode(): String = url.split("/".toRegex()).dropLast(1).last()

        private fun Result.getImageUrl(): String = "$ImageUrl${getCode()}.png"

    }
}