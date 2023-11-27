package com.foodwaste.mubazir.presentation.browse.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodwaste.mubazir.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onDismiss: () -> Unit,
    onReset : () -> Unit,
    filterRadius: String,
    onRadiusChange: (String) -> Unit,
    selectedRadius : FilterRadiusOption?,
    onFilterRadiusSelected: (FilterRadiusOption) -> Unit,
    filterMaxPrice: String,
    onMaxPriceChange: (String) -> Unit,
    isFreeFilterSelected: Boolean,
    onFreeFilterClick: () -> Unit,
    onApplyClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        windowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.height(600.dp)

    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.align(Alignment.TopCenter)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 15.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.text_filter),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    TextButton(onClick = onReset) {
                        Text(
                            text = stringResource(id = R.string.text_reset),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(20.dp, 0.dp)
                        .fillMaxWidth()
                        .verticalScroll(
                            rememberScrollState()
                        ),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    Row {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.text_disctance_from_your_location),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedTextField(
                                    value = filterRadius,
                                    onValueChange = onRadiusChange,
                                    label = {
                                        Text(text = stringResource(R.string.text_radius))
                                    },
                                    trailingIcon = {
                                        Text(
                                            text = stringResource(R.string.text_km),
                                            fontWeight = FontWeight.Bold
                                        )
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                        unfocusedIndicatorColor = Color.LightGray,
                                        focusedIndicatorColor = MaterialTheme.colorScheme.primary
                                    ),
                                    keyboardOptions = KeyboardOptions(
                                        capitalization = KeyboardCapitalization.None,
                                        autoCorrect = true,
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
                                    modifier = Modifier.width(130.dp)
                                )

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {

                                    FilterRadiusOption.values().forEach { filterOption ->
                                        FilterChip(
                                            selected = selectedRadius == filterOption,
                                            onClick = {
                                                onFilterRadiusSelected(filterOption)
                                            },
                                            label = filterOption.textLabel
                                        )
                                    }
                                }
                            }
                        }
                    }


                    Row {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.text_maximum_price),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                OutlinedTextField(
                                    value = filterMaxPrice,
                                    onValueChange = onMaxPriceChange,
                                    label = {
                                        Text(text = stringResource(R.string.text_maximum_price))
                                    },
                                    leadingIcon = {
                                        Text(
                                            text = stringResource(R.string.text_placeholder_rp),
                                            fontWeight = FontWeight.Bold
                                        )
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                        unfocusedIndicatorColor = Color.LightGray,
                                        focusedIndicatorColor = MaterialTheme.colorScheme.primary
                                    ),
                                    keyboardOptions = KeyboardOptions(
                                        capitalization = KeyboardCapitalization.None,
                                        autoCorrect = true,
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
                                    modifier = Modifier.width(200.dp)
                                )

                                FilterChip(
                                    selected = isFreeFilterSelected,
                                    onClick = onFreeFilterClick,
                                    label = stringResource(id = R.string.text_free),
                                )
                            }
                        }
                    }


                }

            }

            Surface(
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 10.dp,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Button(
                    modifier = Modifier
                        .padding(20.dp, 10.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onClick = onApplyClick
                ) {
                    Text(stringResource(id = R.string.text_apply))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChip(
    selected : Boolean,
    onClick : () -> Unit,
    label : String,
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = label,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp, 0.dp)
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = Color.White,
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .height(40.dp)
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
    )
}