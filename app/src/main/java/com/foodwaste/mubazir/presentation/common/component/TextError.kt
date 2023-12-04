package com.foodwaste.mubazir.presentation.common.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun TextError(
    modifier: Modifier = Modifier,
    @StringRes textRes: Int?
) {
    Text(
        text = textRes?.let { stringResource(it) } ?: " ",
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier.padding(top = 4.dp)
    )
}