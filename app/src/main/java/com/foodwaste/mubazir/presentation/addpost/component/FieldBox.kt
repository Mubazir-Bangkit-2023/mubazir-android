package com.foodwaste.mubazir.presentation.addpost.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FieldBox(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onClick: () -> Unit
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
            }
            ,

        ) {

        Text(
            text = if(value == "") placeholder else value,
            color = Color.Gray,
            modifier = Modifier
                .padding(17.dp)
                .align(Alignment.CenterStart)
        )
    }
}