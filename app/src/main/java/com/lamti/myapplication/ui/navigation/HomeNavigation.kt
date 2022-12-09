package com.lamti.myapplication.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.lamti.myapplication.data.repository.model.Pokemon
import com.lamti.myapplication.ui.screens.home.HomeRoute

const val homeRoute = "home_route"

fun NavGraphBuilder.homeScreen(
    pokemons: LazyPagingItems<Pokemon>,
    onNavigateToDetails: (code: Int, color: Int) -> Unit
) {
    composable(route = homeRoute) {
        HomeRoute(
            pokemons = pokemons,
            onNavigateToDetails = onNavigateToDetails
        )
    }
}