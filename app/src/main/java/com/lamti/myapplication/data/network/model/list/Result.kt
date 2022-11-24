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

        fun Result.toPokemon(): Pokemon = Pokemon(
            name = name,
            code = getCode(),
            image = "",
            type1 = "",
            type2 = null
        )

        private fun Result.getCode(): Int = url.split("/".toRegex()).dropLast(1).last().toInt()
    }
}