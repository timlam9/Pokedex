package com.lamti.myapplication.ui.navigation

import android.net.Uri
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.*
import androidx.navigation.compose.composable
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

fun NavGraphBuilder.detailsScreen(onBackClick: () -> Unit) {
    composable(
        route = "$detailsRoute/{$codeArg}/{$colorArg}",
        arguments = listOf(
            navArgument(codeArg) { type = NavType.StringType },
            navArgument(colorArg) { type = NavType.StringType },
        )
    ) {
        DetailsRoute(onBackClick = onBackClick)
    }
}