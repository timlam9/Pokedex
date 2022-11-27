package com.lamti.myapplication.ui.components.details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
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
    imageSize: Dp = 300.dp,
    imageOffset: Dp = (-100).dp,
    translation: Float = 200f,
    roundedCorners: Dp = 24.dp,
) {
    val sheetHeight = (LocalConfiguration.current.screenHeightDp / 2).dp
    val scaffoldState = rememberBottomSheetScaffoldState()

    var animate by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (animate) 1f else 0f)
    val translate by animateFloatAsState(if (animate) 0f else translation)

    LaunchedEffect(Unit) { animate = true }

    Box(modifier = Modifier.fillMaxSize()) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = sheetHeight,
            sheetShape = RoundedCornerShape(
                topStart = roundedCorners,
                topEnd = roundedCorners,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            ),
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
                .size(imageSize)
                .scale(scale)
                .aspectRatio(1f)
                .align(Alignment.Center)
                .offset(y = translate.dp + imageOffset),
        )
    }
}