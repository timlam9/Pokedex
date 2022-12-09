package com.lamti.myapplication.data.repository.model

import androidx.compose.ui.graphics.Color
import com.lamti.myapplication.ui.theme.*

enum class PokemonColor(val value: Color) {
    Dark(Color.DarkGray),
    Normal(Color.LightGray),
    Ghost(Color.Gray),
    Fire(AtkColor),
    Water(DefColor),
    Grass(HPColor),
    Electric(SpdColor),
    Poison(SpAtkColor),
}