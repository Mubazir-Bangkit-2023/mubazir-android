package com.foodwaste.mubazir.presentation.addpost

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor() : ViewModel() {

    private val _uri = MutableStateFlow<Uri?>(Uri.EMPTY)
    val uri = _uri

    private val _titleFieldState = MutableStateFlow("")
    val titleFieldState = _titleFieldState

    private val _categoryState = MutableStateFlow("")
    val categoryState = _categoryState

    private val _categoryId = MutableStateFlow(0)
    val categoryId = _categoryId

    private val _freshnessState = MutableStateFlow("")
    val freshnessState = _freshnessState

    private val _priceFieldState = MutableStateFlow("")
    val priceFieldState = _priceFieldState

    private val _datePickerState = MutableStateFlow(0L)
    val datePickerState = _datePickerState

    private val _timePickerState = MutableStateFlow(0)
    val timePickerState = _timePickerState

    private val _location = MutableStateFlow("")
    val location = _location

    private val _descriptionFieldState = MutableStateFlow("")
    val descriptionFieldState = _descriptionFieldState

    fun onChangeUri(uri: Uri?) {
        _uri.value = uri
    }

    fun onChangeTitle(title: String) {
        _titleFieldState.value = title
    }

    fun onChangeCategory(category: String, categoryId: Int) {
        _categoryState.value = category
        _categoryId.value = categoryId
    }

    fun getFreshnessResult() {
        //TODO getFreshnessUseCase with image uri
        _freshnessState.value = "Fresh banana"
    }

    fun onChangePrice(price: String) {
        _priceFieldState.value = price
    }

    fun onConfirmDatePicker(date: Long) {
        _datePickerState.value = date
    }

    fun onConfirmTimePicker(time: Int) {
        _timePickerState.value = time
    }

    fun onClickLocationField() {
        //TODO getLccationUseCase
        _location.value = "Kedung Badak, Bogor"
    }

    fun onChangeDescription(description: String) {
        _descriptionFieldState.value = description
    }


    //filter input for price
    private fun filterNumericInput(input: String): String {
        return input.filter { it.isDigit() }
    }
}