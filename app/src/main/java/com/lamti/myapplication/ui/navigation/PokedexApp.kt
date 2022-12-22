package com.lamti.myapplication.ui.navigation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.paging.compose.collectAsLazyPagingItems
import com.lamti.pokemonlist.navigation.detailsScreen
import com.lamti.pokemonlist.navigation.homeRoute
import com.lamti.pokemonlist.navigation.homeScreen
import com.lamti.pokemonlist.navigation.navigateToDetails
import com.lamti.pokemonlist.screens.home.HomeViewModel

@Composable
internal fun PokedexApp(
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute,
    statusBarColor: (Color) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val initialColor = MaterialTheme.colors.background
    val pokemons = viewModel.pokemons.collectAsLazyPagingItems()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen(
            pokemons = pokemons,
            onNavigateToDetails = { code, color ->
                val navOptions = navOptions {
                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
                navController.navigateToDetails(code.toString(), color, navOptions)
            }
        )
        detailsScreen(
            pokemons = pokemons,
            onColorChange = {
                statusBarColor(it)
            }
        ) {
            statusBarColor(initialColor)
            navController.popBackStack()
        }
    }
}