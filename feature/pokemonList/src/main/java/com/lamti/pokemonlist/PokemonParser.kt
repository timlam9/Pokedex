package com.lamti.pokemonlist

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.lamti.pokemon.model.PokemonColor
import com.lamti.pokemon.network.model.pokemon.Stat

val WhiteTransparent = Color(0x33FFFFFF)
val BlackTransparent = Color(0x33000000)

val HPColor = Color(0xFF4CAF50)
val AtkColor = Color(0xFFF44336)
val DefColor = Color(0xFF3F51B5)
val SpAtkColor = Color(0xFF673AB7)
val SpDefColor = Color(0xFF9C27B0)
val SpdColor = Color(0xFFCDDC39)

fun PokemonColor.toColor(): Color = when (this) {
    PokemonColor.Dark -> Color.DarkGray
    PokemonColor.Normal -> Color.Gray
    PokemonColor.Ghost -> SpDefColor
    PokemonColor.Fire -> AtkColor
    PokemonColor.Water -> DefColor
    PokemonColor.Grass -> HPColor
    PokemonColor.Electric -> SpdColor
    PokemonColor.Poison -> SpAtkColor
}

fun parseStatToColor(stat: Stat): Color {
    return when (stat.stat.name.lowercase()) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when (stat.stat.name.lowercase()) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}

fun Int.toPokemonCode(): String =
    "#${this.toString().toUpperCase(Locale.current).padStart(3, '0')}"