package com.lamti.myapplication.ui.components.details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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
import androidx.paging.compose.LazyPagingItems
import com.lamti.myapplication.data.repository.model.Pokemon
import com.lamti.myapplication.ui.components.common.PokemonTopBar

@Composable
fun DetailsContent(
    id: Int,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    imageOffset: Dp = (-80).dp,
    roundedCorners: Dp = 24.dp,
    pokemons: LazyPagingItems<Pokemon>,
    onColorChange: (Color) -> Unit,
) {
    val sheetHeight = (LocalConfiguration.current.screenHeightDp / 2).dp
    val scaffoldState = rememberBottomSheetScaffoldState()

    var animate by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (animate) 1f else 0f)

    var currentPage by remember(id) { mutableStateOf(id) }
    val bgColor: Color by remember(currentPage) {
        mutableStateOf(
            pokemons[currentPage]?.color?.value ?: Color.DarkGray
        )
    }

    LaunchedEffect(bgColor) {
        snapshotFlow { bgColor }.collect { color ->
            onColorChange(color)
        }
    }

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
            sheetContent = { BottomSheetContent(pokemons[currentPage]) },
            topBar = {
                PokemonTopBar(
                    color = bgColor,
                    onBackClick = onBackClick
                )
            },
        ) {
            PokemonDetails(
                pokemon = pokemons[currentPage],
                modifier = modifier.background(bgColor),
                height = sheetHeight
            )
        }
        PokemonPager(
            startIndex = id,
            pokemons = pokemons,
            modifier = Modifier
                .offset(y = imageOffset)
                .scale(scale)
                .aspectRatio(1f)
                .align(Alignment.Center),
            onPageChange = {
                currentPage = it
            }
        )
    }
}