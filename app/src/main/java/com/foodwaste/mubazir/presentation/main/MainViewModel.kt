package com.foodwaste.mubazir.presentation.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.domain.usecase.DarkThemeUseCase
import com.foodwaste.mubazir.domain.usecase.GetCurrentLocationUseCase
import com.foodwaste.mubazir.domain.usecase.GetStoredLocationUseCase
import com.foodwaste.mubazir.domain.usecase.GetUserUseCase
import com.foodwaste.mubazir.domain.usecase.SetStoredLocationUseCase
import com.foodwaste.mubazir.presentation.common.LocationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    darkThemeUseCase: DarkThemeUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val context: Context,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val setStoredLocationUseCase: SetStoredLocationUseCase,
    private val getStoredLocationUseCase: GetStoredLocationUseCase
) : ViewModel() {

    private val _locationLoadingState = MutableStateFlow(false)
    val locationLoadingState: StateFlow<Boolean> = _locationLoadingState

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location

    val loggedIn = getUserUseCase()
        .map { it != null }
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val darkTheme = darkThemeUseCase()
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    init {
        getCurrentLocation()
    }

    fun getCurrentLocation() {
        viewModelScope.launch {
            _locationLoadingState.value = true
            try {
                val result = getCurrentLocationUseCase()

                result.onSuccess { location ->
                    setStoredLocationUseCase(location)
                    _location.value = LocationUtils.getAddressFromLocation(location.latitude, location.longitude, context, dispatcher)
                }

                result.onFailure { exception ->
                     getStoredLocationUseCase().collect{
                    _location.value = if(it != null) LocationUtils.getAddressFromLocation(it.latitude, it.longitude, context, dispatcher)  else context.getString(R.string.text_cant_get_location)
                     }
                    Timber.e(exception)
                }
            } catch (securityException: SecurityException) {
                Timber.e(securityException)
            }
            _locationLoadingState.value = false
        }
    }

}