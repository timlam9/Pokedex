package com.lamti.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions

@Composable
fun PokedexApp(modifier: Modifier = Modifier, startDestination: String = homeRoute) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen(
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
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}