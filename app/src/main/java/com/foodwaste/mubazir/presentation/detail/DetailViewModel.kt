package com.foodwaste.mubazir.presentation.detail

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodwaste.mubazir.domain.model.FoodPostDetail
import com.foodwaste.mubazir.domain.usecase.GetDetailPostUseCase
import com.foodwaste.mubazir.presentation.common.LocationUtils
import com.foodwaste.mubazir.presentation.main.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle,
    private val getDetailPostUseCase: GetDetailPostUseCase
) : ViewModel() {

    private val _detailPost = MutableStateFlow<FoodPostDetail?>(null)
    val detailPost = _detailPost.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val _addressState = MutableStateFlow("")
    val addressState = _addressState.asStateFlow()


    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    init {
        loadDetailPost()
    }

    private fun loadDetailPost() {
        viewModelScope.launch(dispatcher) {
            _loadingState.value = true
            val id = savedStateHandle.get<String>(Route.KEY_DETAIL_POST) ?: return@launch
            Timber.d("loadDetailPost: $id")
            getDetailPostUseCase.invoke(id)
                .onSuccess {
                    _detailPost.value = it
                    _addressState.value = LocationUtils.getAddressFromLocation(it.lat, it.lon, context, dispatcher)
                }
                .onFailure {
                    Timber.e(it)
                    _snackbar.emit("Failed to load detail post")
                }

            _loadingState.value = false
        }
    }


}