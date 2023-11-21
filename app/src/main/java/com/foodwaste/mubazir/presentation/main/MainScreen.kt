package com.foodwaste.mubazir.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ManageSearch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.presentation.main.component.NavigationBarMain
import com.foodwaste.mubazir.presentation.main.component.TopBarBasic
import com.foodwaste.mubazir.presentation.main.component.TopBarMain

val NavMenus = listOf(
    Triple(R.string.text_home, Icons.Outlined.Home to Icons.Filled.Home, Route.Home()),
    Triple(
        R.string.text_browse,
        Icons.Outlined.ManageSearch to Icons.Filled.ManageSearch,
        Route.Browse()
    ),
    Triple(R.string.text_article, Icons.Outlined.Article to Icons.Filled.Article, Route.Articles()),
    Triple(
        R.string.text_profile,
        Icons.Outlined.AccountCircle to Icons.Filled.AccountCircle,
        Route.Profile()
    )
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
) {

    val currentBackStack by navController.currentBackStackEntryAsState()
    val isBotNavVisible by remember {
        val screenWithBotNav = NavMenus.map { it.third }

        derivedStateOf {
            when (currentBackStack?.destination?.route) {
                in screenWithBotNav -> true
                else -> false
            }
        }
    }

    Scaffold(
        topBar = {
            if (currentBackStack?.destination?.route == Route.Home() || currentBackStack?.destination?.route == Route.Browse()) {
                TopBarMain(
                    navController = navController,
                    address = "Kedungkandang, Malang"
                )
            } else {
                when (currentBackStack?.destination?.route) {
                    Route.Articles() -> TopBarBasic(
                        navController = navController,
                        title = stringResource(id = R.string.text_article),
                    )

                    Route.Profile() -> TopBarBasic(
                        navController = navController,
                        title = stringResource(id = R.string.text_profile),
                    )
                }
            }
        },
        bottomBar = {
            NavigationBarMain(
                onNavigation = { route ->
                    navController.navigate(route) {
                        popUpTo(Route.Home()) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                botNavVisibilityProvider = { isBotNavVisible },
                currentRouteProvider = { route ->
                    currentBackStack?.destination?.hierarchy?.any { it.route == route } == true
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            CompositionLocalProvider(LocalNavController provides navController) {

                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    NavGraph(
                        startDestination = Route.Home(),
                        navController = navController
                    )
                }
            }
        }
    }
}