package com.lamti.myapplication.ui.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lamti.myapplication.data.repository.model.Pokemon
import com.lamti.myapplication.ui.components.common.PokemonTopBar

@Composable
fun DetailsContent(
    pokemon: Pokemon,
    dominantColor: Color,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val sheetHeight = (LocalConfiguration.current.screenHeightDp / 2).dp
    val scaffoldState = rememberBottomSheetScaffoldState()

    Box(modifier = Modifier.fillMaxSize()) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = sheetHeight,
            sheetShape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp),
            sheetGesturesEnabled = false,
            sheetContent = { BottomSheetContent(pokemon) },
            topBar = {
                PokemonTopBar(
                    color = dominantColor,
                    onBackClick = onBackClick
                )
            },
        ) {
            PokemonDetails(
                pokemon = pokemon,
                modifier = modifier.background(dominantColor),
                height = sheetHeight
            )
        }
        AsyncImage(
            model = pokemon.image,
            contentDescription = pokemon.name,
            modifier = Modifier
                .size(300.dp)
                .aspectRatio(1f)
                .align(Alignment.Center)
                .offset(y = (-100).dp),
        )
    }
}