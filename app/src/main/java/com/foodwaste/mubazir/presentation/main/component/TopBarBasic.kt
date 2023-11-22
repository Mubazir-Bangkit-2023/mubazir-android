package com.foodwaste.mubazir.presentation.main.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarBasic(
    navController: NavHostController,
    title: String,
) {
        TopAppBar(
            title = { Text(text = title, fontWeight = FontWeight.Bold) },
            actions = {
                IconButton(onClick = {
                    navController.navigate("notification")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = null,
                    )

                }
            }
        )
}