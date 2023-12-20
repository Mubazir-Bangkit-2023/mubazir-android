package com.foodwaste.mubazir.presentation.detail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.foodwaste.mubazir.R

@Composable
fun Description(
    description: String,

) {
    val (isExpanded, setExpanded) = remember { mutableStateOf(description.length < 270) }

    val text = if (isExpanded) {
        description
    } else {
        description.take(270) + "..."
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
        )

        if(description.length >= 270) {
            Text(
                text = stringResource(id = if (!isExpanded) R.string.text_read_more else R.string.text_hide),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(0.dp, 2.dp).clickable {
                    setExpanded(!isExpanded)
                }
            )
        }

    }
}