package com.foodwaste.mubazir.presentation.addpost.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.foodwaste.mubazir.R

@Composable
fun FieldBox(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onClick()
            },

        ) {

        Text(
            text = if (value == "") placeholder else value,
            color = if (value == "") Color.Gray else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(17.dp)
                .align(Alignment.CenterStart)
        )
    }
}

@Composable
fun LocationFieldBox(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onClick: () -> Unit,
    onClickRefresh: () -> Unit,
    isLocationLoading: Boolean
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onClick()
            },

        ) {

        if (isLocationLoading) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_loading))
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(50.dp)
            )
        } else {
            Text(
                text = if (value == "") placeholder else value,
                color = if (value == "") Color.Gray else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(17.dp)
                    .align(Alignment.CenterStart)
            )
        }

        IconButton(
            onClick = onClickRefresh,
            modifier = Modifier.align(Alignment.CenterEnd),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Gray
            )
        ) {
            Icon(imageVector = Icons.Outlined.Refresh, contentDescription = "refresh location")
        }

    }
}