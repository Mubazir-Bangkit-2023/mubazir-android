package com.foodwaste.mubazir.presentation.addpost

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.presentation.addpost.component.AddPostTopBar
import com.foodwaste.mubazir.presentation.addpost.component.CategoryDropdown
import com.foodwaste.mubazir.presentation.addpost.component.CheckFreshnessBottomSheet
import com.foodwaste.mubazir.presentation.addpost.component.DatePickerDialog
import com.foodwaste.mubazir.presentation.addpost.component.FieldBox
import com.foodwaste.mubazir.presentation.addpost.component.ImageLoader
import com.foodwaste.mubazir.presentation.addpost.component.TimePickerDialog
import com.foodwaste.mubazir.presentation.common.TimeUtils
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AddPostScreen(
    navController: NavHostController,
    viewModel: AddPostViewModel = hiltViewModel()
) {
    AddPostScreen(
        uri = viewModel.uri,
        onChangeUri = viewModel::onChangeUri,
        titleFieldState = viewModel.titleFieldState,
        onChangeTitle = viewModel::onChangeTitle,
        categoryState = viewModel.categoryState,
        onChangeCategory = viewModel::onChangeCategory,
        selectedCategoryId = viewModel.categoryId,
        freshnessState = viewModel.freshnessState,
        getFreshnessResult = viewModel::getFreshnessResult,
        priceFieldState = viewModel.priceFieldState,
        onChangePrice = viewModel::onChangePrice,
        datePickerState = viewModel.datePickerState,
        onConfirmDatePicker = viewModel::onConfirmDatePicker,
        timePickerState = viewModel.timePickerState,
        onConfirmTimePicker = viewModel::onConfirmTimePicker,
        locationFieldState = viewModel.location,
        onClickLocationField = viewModel::onClickLocationField,
        descriptionFieldState = viewModel.descriptionFieldState,
        onChangeDescription = viewModel::onChangeDescription,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreen(
    uri: StateFlow<Uri?>,
    onChangeUri: (Uri?) -> Unit,
    titleFieldState: StateFlow<String>,
    onChangeTitle: (String) -> Unit,
    categoryState: StateFlow<String>,
    onChangeCategory: (String, Int) -> Unit,
    selectedCategoryId: StateFlow<Int>,
    freshnessState: StateFlow<String>,
    getFreshnessResult: () -> Unit,
    priceFieldState: StateFlow<String>,
    onChangePrice: (String) -> Unit,
    datePickerState: StateFlow<Long>,
    onConfirmDatePicker: (Long) -> Unit,
    timePickerState: StateFlow<Int>,
    onConfirmTimePicker: (Int) -> Unit,
    locationFieldState: StateFlow<String>,
    onClickLocationField: () -> Unit,
    descriptionFieldState: StateFlow<String>,
    onChangeDescription: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            AddPostTopBar(
                onClickSubmitButton = {},
                isSubmitButtonEnabled = false
            )
        }
    ) { innerPadding ->
        val focusManager = LocalFocusManager.current

        //bottom sheet
        var openBottomSheet by rememberSaveable { mutableStateOf(false) }
//        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        Column(
            modifier = Modifier
                .padding(10.dp, innerPadding.calculateTopPadding(), 10.dp, 0.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            //Image loader
            ImageLoader(uri, onChangeUri)


            //Title field
            val title by titleFieldState.collectAsState()
            OutlinedTextField(
                value = title,
                onValueChange = onChangeTitle,
                singleLine = true,
                label = {
                    Text(text = stringResource(id = R.string.text_title), color = Color.Gray)
                },
                shape = RoundedCornerShape(10.dp),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )

            //Category dropdown
            CategoryDropdown(categoryState, onChangeCategory)

            //Check freshness only for food ingredient
            val selectedCategory by selectedCategoryId.collectAsState()
            if (selectedCategory == 3) {
                val freshness by freshnessState.collectAsState()

                FieldBox(
                    value = freshness.ifEmpty { freshness },
                    placeholder = stringResource(id = R.string.text_check_freshness),
                    onClick = {
                        openBottomSheet = true
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            //Price field
            val price by priceFieldState.collectAsState()

            OutlinedTextField(
                value = price,
                onValueChange = onChangePrice,
                singleLine = true,
                label = {
                    Text(text = stringResource(id = R.string.text_price), color = Color.Gray)
                },
                shape = RoundedCornerShape(10.dp),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )

            //Pick up time
            var openDateDialog by remember { mutableStateOf(false) }
            var openTimeDialog by remember { mutableStateOf(false) }

            Column {
                Text(stringResource(id = R.string.text_pickup_time) + ":")
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    val date by datePickerState.collectAsState()
                    val time by timePickerState.collectAsState()

                    FieldBox(
                        value = if (date != 0L) TimeUtils.formatSelectedDate(date) else "",
                        placeholder = stringResource(id = R.string.text_select_date),
                        onClick = {
                            openDateDialog = true
                        },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    FieldBox(
                        value = if (time != 0) TimeUtils.formatSelectedTime(time) else "",
                        placeholder = stringResource(id = R.string.text_select_time),
                        onClick = {
                            openTimeDialog = true
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            //Location
            val location by locationFieldState.collectAsState()
            FieldBox(
                value = location.ifEmpty { "" },
                placeholder = stringResource(id = R.string.text_location),
                onClick = {
                    onClickLocationField()
                },
                modifier = Modifier.fillMaxWidth()
            )

            //Description
            val description by descriptionFieldState.collectAsState()
            OutlinedTextField(
                value = description,
                onValueChange = onChangeDescription,
                minLines = 4,
                maxLines = 4,
                label = {
                    Text(text = stringResource(id = R.string.text_description), color = Color.Gray)
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth(),
            )


            if (openDateDialog) {
                DatePickerDialog(
                    onDismiss = { openDateDialog = false },
                    onConfirm = {
                        onConfirmDatePicker(it)
                        openDateDialog = false
                    }
                )
            }

            if (openTimeDialog) {
                TimePickerDialog(onDismiss = { openTimeDialog = false }, onConfirm = {
                    onConfirmTimePicker(it)
                    openTimeDialog = false
                })
            }

        }
        if (openBottomSheet) {

            CheckFreshnessBottomSheet(
                onDismiss = {
                    openBottomSheet = false
                },
                sheetState = bottomSheetState,
                uri = uri,
                freshnessValue = freshnessState,
                onClickResultButton = getFreshnessResult
            )
        }
    }
}


//@Preview
//@Composable
//fun AddPostScreenPreview() {
//    AddPostScreen(
//        uri = MutableStateFlow(null),
//        onChangeUri = {},
//        titleFieldState = MutableStateFlow(""),
//        onChangeTitle = {},
//        categoryState = MutableStateFlow(""),
//        onChangeCategory = { _, _ -> },
//        selectedCategoryId = MutableStateFlow(0),
//        priceFieldState = MutableStateFlow(""),
//        onChangePrice = {},
//    )
//}
