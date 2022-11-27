package com.lamti.myapplication.ui.navigation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions

@Composable
fun PokedexApp(
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute,
    statusBarColor: (Color) -> Unit,
) {
    val navController = rememberNavController()
    val initialColor = MaterialTheme.colors.background

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
                statusBarColor(Color(color))
                navController.navigateToDetails(code.toString(), color, navOptions)
            }
        )
        detailsScreen(
            onBackClick = {
                statusBarColor(initialColor)
                navController.popBackStack()
            }
        )
    }
}