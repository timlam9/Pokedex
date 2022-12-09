package com.lamti.myapplication.data.network.model.pokemon


import com.lamti.myapplication.data.repository.model.Pokemon
import com.lamti.myapplication.data.repository.model.PokemonColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPokemon(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("sprites")
    val sprites: Sprites,
    @SerialName("stats")
    val stats: List<Stat>,
    @SerialName("types")
    val types: List<Type>,
) {

    companion object {

        fun NetworkPokemon.toPokemon(): Pokemon = Pokemon(
            name = name,
            code = id,
            image = sprites.other?.officialArtwork?.frontDefault ?: "",
            type1 = types.first().type.name,
            type2 = if (types.size > 1) types[1].type.name else null,
            stats = stats,
            color = types.first().type.name.toPokemonColor()
        )
    }
}

fun String.toPokemonColor(): PokemonColor = when {
    this.equals(PokemonColor.Dark.name, true) -> PokemonColor.Dark
    this.equals(PokemonColor.Normal.name, true) -> PokemonColor.Normal
    this.equals(PokemonColor.Fire.name, true) -> PokemonColor.Fire
    this.equals(PokemonColor.Water.name, true) -> PokemonColor.Water
    this.equals(PokemonColor.Grass.name, true) -> PokemonColor.Grass
    this.equals(PokemonColor.Electric.name, true) -> PokemonColor.Electric
    this.equals(PokemonColor.Poison.name, true) -> PokemonColor.Poison
    else -> PokemonColor.Ghost
}

