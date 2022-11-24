package com.lamti.myapplication.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lamti.myapplication.ui.navigation.detailsScreen
import com.lamti.myapplication.ui.navigation.homeRoute
import com.lamti.myapplication.ui.navigation.homeScreen
import com.lamti.myapplication.ui.navigation.navigateToDetails

@Composable
fun PokedexApp(modifier: Modifier = Modifier, startDestination: String = homeRoute) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen(
            onNavigateToDetails = { code ->
                navController.navigateToDetails(code.toString())
            }
        )
        detailsScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}