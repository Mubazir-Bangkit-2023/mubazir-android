package com.foodwaste.mubazir.presentation.userprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.domain.model.User

@Composable
fun UserProfileScreen(
    navController: NavHostController,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    UserProfileScreen(user = user, onClickBack = { navController.popBackStack() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    user: User?,
    onClickBack: () -> Unit = { },
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.text_user_profile)) },
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                if (user?.photo == null) {
                    Image(
                        painter = painterResource(R.drawable.ic_user),
                        contentDescription = "profile picture",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(
                                CircleShape
                            )
                    )
                } else {
                    AsyncImage(
                        model = user.photo,
                        contentDescription = "profile picture",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(
                                CircleShape
                            )
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))


                OutlinedTextField(
                    value = user?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    shape = RoundedCornerShape(30.dp),
                    label = {
                        Text(text = stringResource(id = R.string.text_full_name))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Badge,
                            contentDescription = null
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
                    ),

                )

                OutlinedTextField(
                    value = user?.email ?: "",
                    onValueChange = {},
                    readOnly = true,
                    shape = RoundedCornerShape(30.dp),
                    label = {
                        Text(text = stringResource(id = R.string.text_email))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.AlternateEmail,
                            contentDescription = null
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
                    ),

                    )

                OutlinedTextField(
                    value = user?.noHp ?: "",
                    onValueChange = {},
                    readOnly = true,
                    shape = RoundedCornerShape(30.dp),
                    label = {
                        Text(text = stringResource(id = R.string.text_phone_number))
                    },
                    leadingIcon = {
                        Text(
                            text = stringResource(id = R.string.text_phone_code),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
                    ),

                    )
            }
        }
    }
}



