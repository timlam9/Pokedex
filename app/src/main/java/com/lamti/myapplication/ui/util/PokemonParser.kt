package com.lamti.myapplication.ui.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.lamti.myapplication.data.network.model.pokemon.Stat
import com.lamti.myapplication.ui.theme.*

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