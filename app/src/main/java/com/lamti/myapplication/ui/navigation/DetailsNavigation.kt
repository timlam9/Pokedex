package com.lamti.myapplication.ui.navigation

import android.net.Uri
import androidx.annotation.VisibleForTesting
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.lamti.myapplication.ui.screens.details.DetailsRoute

@VisibleForTesting
internal const val codeArg = "code"

@VisibleForTesting
internal const val colorArg = "color"
internal const val detailsRoute = "details_route"

internal class DetailsArgs(val code: String, val color: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        checkNotNull(savedStateHandle[codeArg]) as String,
        checkNotNull(savedStateHandle[colorArg]) as String
    )
}

fun NavController.navigateToDetails(code: String, color: Int, navOptions: NavOptions) {
    val encodedString = Uri.encode(code)
    val encodedString2 = Uri.encode(color.toString())
    this.navigate("$detailsRoute/$encodedString/$encodedString2", navOptions)
}

fun NavGraphBuilder.detailsScreen(
    pokemons: LazyPagingItems<com.lamti.pokemon.model.Pokemon>,
    onColorChange: (Color) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(
        route = "$detailsRoute/{$codeArg}/{$colorArg}",
        arguments = listOf(
            navArgument(codeArg) { type = NavType.StringType },
            navArgument(colorArg) { type = NavType.StringType },
        )
    ) {
        val id = (it.arguments?.getString(codeArg)?.toInt() ?: 1) - 1
        val dominantColor = Color(it.arguments?.getString(colorArg)?.toLong() ?: 0xFF000000)

        DetailsRoute(
            page = id,
            pokemons = pokemons,
            onBackClick = onBackClick,
            dominantColor = dominantColor,
            onColorChange = onColorChange
        )
    }
}