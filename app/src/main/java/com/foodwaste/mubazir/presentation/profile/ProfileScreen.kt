package com.foodwaste.mubazir.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.foodwaste.mubazir.R

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    ProfileScreen(
        onClickLogout = viewModel::signOut
    )
}

@Composable
fun ProfileScreen(
    onClickMyProfile: () -> Unit = {},
    onClickMyFoodPost: () -> Unit = {},
    onClickSettings: () -> Unit = {},
    onClickLogout: () -> Unit = {},
) {
    val openLogoutDialog = remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "user picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Muhammad Rizki", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)


            Spacer(modifier = Modifier.height(50.dp))

            ElevatedCard(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                ),
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    // My profile button
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onClickMyProfile()
                            }
                            .padding(15.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "My Profile"
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(text = stringResource(id = R.string.text_my_profile))
                        }
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }

                    // My Food FoodPostResponse
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onClickMyFoodPost()
                            }
                            .padding(15.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Article,
                                contentDescription = "My Food FoodPostResponse"
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(text = stringResource(id = R.string.text_my_food_post))
                        }
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }

                    //Settings
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onClickSettings()
                            }
                            .padding(15.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = "Settings"
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(text = stringResource(id = R.string.text_settings))
                        }
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }

                    //Logout
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                openLogoutDialog.value = true
                            }
                            .padding(15.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Logout,
                                contentDescription = "Logout"
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(text = stringResource(id = R.string.text_logout))
                        }
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }
            }
        }

    }


    if (openLogoutDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openLogoutDialog.value = false
            },
            title = {
                Text(stringResource(id = R.string.text_logout))
            },
            text = {
                Text(text = stringResource(id = R.string.text_logout_confirmation))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openLogoutDialog.value = false
                        onClickLogout()
                    }
                ) {
                    Text(stringResource(id = R.string.text_logout))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openLogoutDialog.value = false
                    }
                ) {
                    Text(stringResource(id = R.string.text_cancel))
                }
            }
        )
    }
}

