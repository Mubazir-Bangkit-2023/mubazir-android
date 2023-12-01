package com.foodwaste.mubazir.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        Route.Home.composable()
        Route.Browse.composable()
        Route.Articles.composable()
        Route.Profile.composable()
        Route.Notification.composable()
        Route.AddPost.composable()
        Route.Maps.composable()
        Route.SignIn.composable()
        Route.SignUp.composable()
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("Nav Controller not provided")
}