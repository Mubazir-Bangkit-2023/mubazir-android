package com.foodwaste.mubazir.presentation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.foodwaste.mubazir.presentation.addpost.AddPostScreen
import com.foodwaste.mubazir.presentation.browse.BrowseScreen
import com.foodwaste.mubazir.presentation.detail.DetailScreen
import com.foodwaste.mubazir.presentation.home.HomeScreen
import com.foodwaste.mubazir.presentation.maps.MapsScreen
import com.foodwaste.mubazir.presentation.myfoodpost.MyFoodPostScreen
import com.foodwaste.mubazir.presentation.notification.NotificationScreen
import com.foodwaste.mubazir.presentation.profile.ProfileScreen
import com.foodwaste.mubazir.presentation.signin.SignInScreen
import com.foodwaste.mubazir.presentation.signup.SignUpScreen
import com.foodwaste.mubazir.presentation.userprofile.UserProfileScreen


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

    object Detail : Route("detail/{$KEY_DETAIL_POST}") {
        private val arguments = listOf(
            navArgument(KEY_DETAIL_POST) {
                type = NavType.StringType
            }
        )

        context(NavGraphBuilder)
        fun composable() = composable(route, arguments) {
            DetailScreen(LocalNavController.current)
        }

        operator fun invoke(id: String) = "detail/$id"

    }

    object UserProfile : Route("user_profile") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            UserProfileScreen(LocalNavController.current)
        }

        operator fun invoke() = "user_profile"
    }

    object MyFoodPost : Route("my_food_post") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            MyFoodPostScreen(LocalNavController.current)
        }

        operator fun invoke() = "my_food_post"
    }

    companion object {
        const val KEY_DETAIL_POST = "detail_post"
    }
}
