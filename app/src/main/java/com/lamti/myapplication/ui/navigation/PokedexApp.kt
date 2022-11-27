package com.lamti.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

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
                navController.navigateToDetails(code.toString(), color)
            }
        )
        detailsScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}