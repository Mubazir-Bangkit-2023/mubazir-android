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
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.domain.model.User
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    ProfileScreen(
        onClickMyProfile = {
            navController.navigate("user_profile")
        },
        onClickMyFoodPost = {
            navController.navigate("my_food_post")
        },
        onClickLogout = viewModel::signOut,
        userState = viewModel.user,
        darkTheme = viewModel.darkTheme,
        onSaveTheme = viewModel::setDarkTheme
    )
}

@Composable
fun ProfileScreen(
    onClickMyProfile: () -> Unit = {},
    onClickMyFoodPost: () -> Unit = {},
    onClickLogout: () -> Unit = {},
    userState: StateFlow<User?>,
    darkTheme: StateFlow<Boolean?>,
    onSaveTheme: (Boolean) -> Unit = {}
) {
    val openLogoutDialog = remember { mutableStateOf(false) }

    val openThemeDialog = remember { mutableStateOf(false) }

    val user by userState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            if (user?.photo == null) {
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "user picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
            } else {
                AsyncImage(
                    model = user?.photo, contentDescription = "user picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = user?.name ?: "", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)


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

                    //Theme
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                openThemeDialog.value = true
                            }
                            .padding(15.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.DarkMode,
                                contentDescription = "Theme"
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(text = stringResource(id = R.string.text_theme))
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

    if (openThemeDialog.value) {
        val darkTheme by darkTheme.collectAsState()

        var isDarkTheme by remember { mutableStateOf(darkTheme) }
        AlertDialog(
            onDismissRequest = {
                openThemeDialog.value = false
            },
            title = {
                Text(stringResource(id = R.string.text_theme))
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),

                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isDarkTheme == false,
                            onClick = { isDarkTheme = false })
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            stringResource(id = R.string.text_light_mode),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),

                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isDarkTheme == true,
                            onClick = { isDarkTheme = true })
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            stringResource(id = R.string.text_dark_mode),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openThemeDialog.value = false
                        onSaveTheme(isDarkTheme ?: false)
                    }
                ) {
                    Text(stringResource(id = R.string.text_save))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openThemeDialog.value = false
                    }
                ) {
                    Text(
                        stringResource(id = R.string.text_cancel),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        )
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
