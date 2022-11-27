package com.lamti.myapplication.ui.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lamti.myapplication.ui.components.common.PokemonError
import com.lamti.myapplication.ui.components.common.PokemonLoader
import com.lamti.myapplication.ui.components.details.DetailsContent

@Composable
internal fun DetailsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val uiState: DetailsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailsScreen(
        uiState = uiState,
        modifier = modifier,
        onBackClick = onBackClick,
    )
}

@Composable
fun DetailsScreen(
    uiState: DetailsUiState,
    modifier: Modifier,
    onBackClick: () -> Unit,
) {
    BackHandler {
        onBackClick()
    }

    when (uiState) {
        DetailsUiState.Error -> PokemonError()
        is DetailsUiState.Loading -> PokemonLoader(
            modifier = modifier.background(uiState.dominantColor),
            size = 200.dp
        )
        is DetailsUiState.Success -> {
            with(uiState) {
                DetailsContent(
                    pokemon = pokemon,
                    dominantColor = dominantColor,
                    modifier = modifier,
                    onBackClick = onBackClick
                )
            }
        }
    }
}