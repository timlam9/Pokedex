package com.lamti.myapplication.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lamti.myapplication.ui.screens.HomeRoute

const val homeRoute = "home_route"

fun NavGraphBuilder.homeScreen(onNavigateToDetails: (code: Int) -> Unit) {
    composable(route = homeRoute) {
        HomeRoute(onNavigateToDetails)
    }
}