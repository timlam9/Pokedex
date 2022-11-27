package com.lamti.myapplication.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lamti.myapplication.R

val pokemonFont: FontFamily = FontFamily(Font(R.font.pokemon_solid))
val mondayFeelingsFont: FontFamily = FontFamily(Font(R.font.monday_feelings))
val sparkyStonesFont: FontFamily = FontFamily(Font(R.font.sparky_stones))

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = mondayFeelingsFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = mondayFeelingsFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = sparkyStonesFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h2 = TextStyle(
        fontFamily = pokemonFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = 4.sp,
    )
)