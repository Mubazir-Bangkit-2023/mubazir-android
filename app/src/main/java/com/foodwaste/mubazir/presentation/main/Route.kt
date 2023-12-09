package com.foodwaste.mubazir.presentation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.foodwaste.mubazir.presentation.addpost.AddPostScreen
import com.foodwaste.mubazir.presentation.articles.ArticlesScreen
import com.foodwaste.mubazir.presentation.browse.BrowseScreen
import com.foodwaste.mubazir.presentation.home.HomeScreen
import com.foodwaste.mubazir.presentation.maps.MapsScreen
import com.foodwaste.mubazir.presentation.notification.NotificationScreen
import com.foodwaste.mubazir.presentation.profile.ProfileScreen
import com.foodwaste.mubazir.presentation.signin.SignInScreen
import com.foodwaste.mubazir.presentation.signup.SignUpScreen


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

    object Profile : Route("profile") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            ProfileScreen(LocalNavController.current)
        }

        operator fun invoke() = route

    }

    object Notification : Route("notification") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            NotificationScreen(LocalNavController.current)
        }

        operator fun invoke() = route

    }

    object Maps : Route("maps") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            MapsScreen(LocalNavController.current)
        }

        operator fun invoke() = route

    }

    object AddPost : Route("add_post") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            AddPostScreen(LocalNavController.current)
        }

        operator fun invoke() = route

    }

    object SignIn : Route("sign_in") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            SignInScreen(LocalNavController.current)
        }

        operator fun invoke() = route

    }

    object SignUp : Route("sign_up") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            SignUpScreen(LocalNavController.current)
        }

        operator fun invoke() = route

    }
}
