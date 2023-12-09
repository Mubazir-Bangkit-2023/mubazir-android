package com.foodwaste.mubazir.presentation.addpost.component

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.foodwaste.mubazir.R
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckFreshnessBottomSheet(
    freshnessValue: StateFlow<String>,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    uri: StateFlow<Uri?>,
    onClickResultButton : () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        windowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.height(500.dp)

    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.text_check_freshness),
                    style = MaterialTheme.typography.headlineSmall
                )
                TextButton(onClick = onDismiss ) {
                    Text(text = stringResource(id = R.string.text_close))
                }

            }

            val imageUri by uri.collectAsState()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                if (imageUri?.path?.isNotEmpty() == true) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Food image",
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Center),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.text_no_image),
                        modifier = Modifier.align(Center)
                    )
                }
            }


            Column(
                modifier = Modifier.align(CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                val freshnessValue by freshnessValue.collectAsState()

                Text(text = stringResource(id = R.string.text_result), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Text(text = freshnessValue, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }


            Button(onClick = onClickResultButton, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.text_get_result))
            }
        }
    }
}

