package com.lamti.myapplication.ui.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.lamti.myapplication.ui.components.common.PokemonError
import com.lamti.myapplication.ui.components.common.PokemonLoader
import com.lamti.myapplication.ui.components.details.DetailsContent

@Composable
internal fun DetailsRoute(
    page: Int,
    dominantColor: Color,
    onBackClick: () -> Unit,
    onColorChange: (Color) -> Unit,
    modifier: Modifier = Modifier,
    pokemons: LazyPagingItems<com.lamti.pokemon.model.Pokemon>
) {
    DetailsScreen(
        id = page,
        pokemons = pokemons,
        modifier = modifier,
        dominantColor = dominantColor,
        onColorChange = onColorChange,
        onBackClick = onBackClick
    )
}

@Composable
fun DetailsScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    pokemons: LazyPagingItems<com.lamti.pokemon.model.Pokemon>,
    id: Int,
    dominantColor: Color,
    onColorChange: (Color) -> Unit,
) {
    BackHandler { onBackClick() }

    when {
        pokemons.loadState.refresh is LoadState.Error && pokemons.itemCount <= 0 -> PokemonError()
        pokemons.loadState.refresh is LoadState.Loading -> PokemonLoader(
            modifier = modifier.background(dominantColor),
            size = 200.dp
        )
        else -> {
            DetailsContent(
                id = id,
                modifier = modifier,
                onBackClick = onBackClick,
                pokemons = pokemons,
                onColorChange = onColorChange
            )
        }
    }
}