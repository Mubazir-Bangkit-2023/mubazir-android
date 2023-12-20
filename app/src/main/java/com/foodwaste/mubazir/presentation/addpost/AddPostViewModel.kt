package com.foodwaste.mubazir.presentation.addpost

import android.content.Context
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.domain.usecase.FoodClassificationUseCase
import com.foodwaste.mubazir.domain.usecase.GetCurrentLocationUseCase
import com.foodwaste.mubazir.domain.usecase.GetStoredLocationUseCase
import com.foodwaste.mubazir.domain.usecase.SetStoredLocationUseCase
import com.foodwaste.mubazir.domain.usecase.UploadFoodPostUseCase
import com.foodwaste.mubazir.presentation.common.ImageUtils
import com.foodwaste.mubazir.presentation.common.LocationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val context: Context,
    private val foodClassificationUseCase: FoodClassificationUseCase,
    private val getStoredLocationUseCase: GetStoredLocationUseCase,
    private val setStoredLocationUseCase: SetStoredLocationUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val uploadFoodPostUseCase: UploadFoodPostUseCase
) : ViewModel() {

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState

    private val _uploadSuccessEvent = MutableStateFlow(false)
    val uploadSuccessEvent get() = _uploadSuccessEvent


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

    private val _freshnessLoading = MutableStateFlow(false)
    val freshnessLoading = _freshnessLoading

    private val _priceFieldState = MutableStateFlow("")
    val priceFieldState = _priceFieldState

    private val _datePickerState = MutableStateFlow(0L)
    val datePickerState = _datePickerState

    private val _timePickerState = MutableStateFlow(0)
    val timePickerState = _timePickerState

    private val _location = MutableStateFlow<Location?>(null)
    val location = _location

    private val _locationString = MutableStateFlow("")
    val locationString = _locationString

    private val _locationLoading = MutableStateFlow(false)
    val locationLoading = _locationLoading

    private val _descriptionFieldState = MutableStateFlow("")
    val descriptionFieldState = _descriptionFieldState

    fun onChangeUri(uri: Uri?) {
        _uri.value = uri
    }

    fun onDeleteImage() {
        _uri.value = Uri.EMPTY
        _freshnessState.value = ""
    }

    fun onChangeTitle(title: String) {
        _titleFieldState.value = title
    }

    fun onChangeCategory(category: String, categoryId: Int) {
        _categoryState.value = category
        _categoryId.value = categoryId
    }


    fun onChangePrice(price: String) {
        _priceFieldState.value = price
    }

    fun onConfirmDatePicker(date: Long) {
        _datePickerState.value = date / 1000
    }

    fun onConfirmTimePicker(time: Int) {
        _timePickerState.value = time
    }

    fun onClickLocationField() {
        viewModelScope.launch {
            getStoredLocationUseCase().collect {
                _location.value = it
                _locationString.value = LocationUtils.getAddressFromLocation(
                    _location.value!!.latitude,
                    _location.value!!.longitude,
                    context,
                    dispatcher
                )
                Timber.d("onClickLocationField: $it")
            }

        }
    }

    fun onRefreshLocation() {
        viewModelScope.launch {
            _locationLoading.value = true
            try {
                val result = getCurrentLocationUseCase()

                result.onSuccess { location ->
                    setStoredLocationUseCase(location)
                    _location.value = location
                    _locationString.value = LocationUtils.getAddressFromLocation(
                        _location.value!!.latitude,
                        _location.value!!.longitude,
                        context,
                        dispatcher
                    )
                }

                result.onFailure { exception ->
                    getStoredLocationUseCase().collect {
                        if (it != null) {
                            _location.value = it
                            _locationString.value = LocationUtils.getAddressFromLocation(
                                it.latitude,
                                it.longitude,
                                context,
                                dispatcher
                            )
                        }
                    }
                    Timber.e(exception)
                }
            } catch (securityException: SecurityException) {
                Timber.e(securityException)
            }
            _locationLoading.value = false
        }
    }

    fun onChangeDescription(description: String) {
        _descriptionFieldState.value = description
    }

    fun getFreshnessResult() {
        viewModelScope.launch(dispatcher) {
            _freshnessLoading.value = true
            if (_uri.value != Uri.EMPTY) {
                val imageFile = _uri.value?.let { ImageUtils.uriToFile(it, context) }

                val requestImageFile = imageFile?.asRequestBody("image/jpeg".toMediaType())
                val multipartBody =
                    requestImageFile?.let { req ->
                        MultipartBody.Part.createFormData(
                            "img", imageFile.name,
                            req
                        )
                    }
                foodClassificationUseCase.invoke(multipartBody!!)
                    .onSuccess {
                        _freshnessState.value = it
                    }
                    .onFailure {
                        Timber.e(it)
                        Toast.makeText(
                            context,
                            context.getString(R.string.text_failed_get_result),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
            _freshnessLoading.value = false
        }

    }


    val fulFilled = combine(
        uri, titleFieldState, categoryId, priceFieldState,
        datePickerState, timePickerState, location, descriptionFieldState
    ) { values ->
        values.all { it != null && it != Uri.EMPTY && it.toString().isNotBlank() }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = false
    )

    fun upload() {
        viewModelScope.launch(dispatcher) {
            if (!fulFilled.value) {
                Toast.makeText(
                    context,
                    context.getString(R.string.text_fill_all_fields),
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }
            _loadingState.value = true
            val imageFile = _uri.value?.let { ImageUtils.uriToFile(it, context) }
            val requestImageFile = imageFile?.asRequestBody("image/jpeg".toMediaType())
            val multipartBody =
                requestImageFile?.let { req ->
                    MultipartBody.Part.createFormData(
                        "image", imageFile.name,
                        req
                    )
                }
            val price = filterNumericInput(_priceFieldState.value).toInt()
            val timePickUp = _datePickerState.value + _timePickerState.value.toLong()

            uploadFoodPostUseCase.invoke(
                title = _titleFieldState.value,
                categoryId = _categoryId.value.toString(),
                freshness = if(_freshnessState.value == "") null else _freshnessState.value,
                price = price.toString(),
                pickupTime = timePickUp.toString(),
                lat = _location.value?.latitude.toString(),
                lon = _location.value?.longitude.toString(),
                description = _descriptionFieldState.value,
                image = multipartBody!!
            ).fold(
                onSuccess = {
                    _loadingState.value = false
                    _uploadSuccessEvent.value = true
                    _snackbar.emit(context.getString(R.string.text_upload_success))
                },
                onFailure = {
                    _loadingState.value = false
                    _snackbar.emit(context.getString(R.string.text_upload_failed))
                    Timber.e(it)
                }
            )
        }
    }

    fun resetUploadSuccessEvent() {
        _uploadSuccessEvent.value = false
    }

    private fun filterNumericInput(input: String): String {
        return input.filter { it.isDigit() }
    }
}