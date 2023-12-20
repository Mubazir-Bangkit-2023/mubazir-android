package com.foodwaste.mubazir.presentation.addpost.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.foodwaste.mubazir.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostTopBar(
    onClickBack: () -> Unit,
    onClickSubmitButton: () -> Unit,
    isSubmitButtonEnabled: Boolean
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.text_post_food)) },
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            TextButton(
                onClick = onClickSubmitButton,
                enabled = isSubmitButtonEnabled,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Black,
                    disabledContentColor = Color.Gray
                )
            ) {
                Text(text = stringResource(id = R.string.text_publish))
            }
        }
    )
}
