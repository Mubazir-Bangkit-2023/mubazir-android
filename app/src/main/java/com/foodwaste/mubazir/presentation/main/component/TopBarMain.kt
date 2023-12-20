package com.foodwaste.mubazir.presentation.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.foodwaste.mubazir.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMain(
    navController: NavHostController,
    address: String,
    onCLickAddress: () -> Unit,
    isAddressLoading: Boolean
) {
    CenterAlignedTopAppBar(

        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.mubazir_icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(26.dp)
            )
        },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (isAddressLoading) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_loading))
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(80.dp)
                    )
                } else {
                    Text(
                        text = stringResource(R.string.text_your_location),
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = address,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable { onCLickAddress() })
                }
            }
        },
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
