package com.lamti.pokemonlist.components.details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.lamti.pokemon.model.Pokemon
import com.lamti.pokemonlist.components.common.PokemonTopBar
import com.lamti.pokemonlist.toColor

@OptIn(ExperimentalMaterial3Api::class)
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
    val scale by animateFloatAsState(if (animate) 1f else 0f, label = "animate scale")

    var currentPage by remember(id) { mutableIntStateOf(id) }
    val bgColor: Color by remember(currentPage) {
        mutableStateOf(
            pokemons[currentPage]?.color?.toColor() ?: Color.DarkGray
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
            sheetShadowElevation = 0.dp,
            scaffoldState = scaffoldState,
            sheetPeekHeight = sheetHeight,
            sheetShape = RoundedCornerShape(
                topStart = roundedCorners,
                topEnd = roundedCorners,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            ),
            sheetContent = { BottomSheetContent(pokemons[currentPage]) },
            topBar = {
                PokemonTopBar(
                    color = bgColor,
                    onBackClick = onBackClick
                )
            },
            sheetDragHandle = null,
            sheetSwipeEnabled = false,
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
