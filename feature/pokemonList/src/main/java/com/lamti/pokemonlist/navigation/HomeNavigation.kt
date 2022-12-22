package com.lamti.pokemonlist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.lamti.pokemonlist.screens.home.HomeRoute

const val homeRoute = "home_route"

fun NavGraphBuilder.homeScreen(
    pokemons: LazyPagingItems<com.lamti.pokemon.model.Pokemon>,
    onNavigateToDetails: (code: Int, color: Int) -> Unit
) {
    composable(route = homeRoute) {
        HomeRoute(
            pokemons = pokemons,
            onNavigateToDetails = onNavigateToDetails
        )
    }
}