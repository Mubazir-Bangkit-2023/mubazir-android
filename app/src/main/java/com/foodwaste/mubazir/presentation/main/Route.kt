package com.foodwaste.mubazir.presentation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.foodwaste.mubazir.presentation.articles.ArticlesScreen
import com.foodwaste.mubazir.presentation.browse.BrowseScreen
import com.foodwaste.mubazir.presentation.home.HomeScreen
import com.foodwaste.mubazir.presentation.profile.ProfileScreen


sealed class Route(protected val route: String) {

    object Home : Route("home") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            HomeScreen(LocalNavController.current)
        }

        operator fun invoke() = route
    }

    object Browse : Route("browse") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            BrowseScreen(LocalNavController.current)
        }

        operator fun invoke() = route

    }

    object Articles : Route("articles") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            ArticlesScreen(LocalNavController.current)
        }

        operator fun invoke() = route

    }

    object Profile : Route("profile") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            ProfileScreen(LocalNavController.current)
        }

        operator fun invoke() = route

    }
}
