package com.lamti.myapplication.ui.navigation

import android.net.Uri
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lamti.myapplication.ui.screens.DetailsRoute

@VisibleForTesting
internal const val codeArg = "code"
internal const val detailsRoute = "details_route"

internal class DetailsArgs(val code: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle[codeArg]) as String)
}

fun NavController.navigateToDetails(code: String) {
    val encodedString = Uri.encode(code)
    this.navigate("$detailsRoute/$encodedString")
}

fun NavGraphBuilder.detailsScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "$detailsRoute/{$codeArg}",
        arguments = listOf(
            navArgument(codeArg) { type = NavType.StringType }
        )
    ) {
        DetailsRoute(onBackClick = onBackClick)
    }
}