package com.lamti.pokemon.network.model.list

import com.lamti.pokemon.model.Pokemon
import com.lamti.pokemon.network.model.list.Result.Companion.toPokemon
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPokemonList(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String?,
    @SerialName("previous")
    val previous: String?,
    @SerialName("results")
    val results: List<Result>
) {

    companion object {

        fun NetworkPokemonList.toPokemons(): List<Pokemon> = results.map { it.toPokemon() }
    }
}