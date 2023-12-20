package com.foodwaste.mubazir.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodwaste.mubazir.domain.usecase.DarkThemeUseCase
import com.foodwaste.mubazir.domain.usecase.GetUserUseCase
import com.foodwaste.mubazir.domain.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val darkThemeUseCase: DarkThemeUseCase,
    dispatcher: CoroutineDispatcher,
    getUserUseCase: GetUserUseCase
) : ViewModel() {

    val user = getUserUseCase().flowOn(dispatcher).stateIn(viewModelScope, SharingStarted.Lazily, null)

    val darkTheme = darkThemeUseCase()
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun setDarkTheme(isDarkTheme: Boolean?) {
        viewModelScope.launch {
            darkThemeUseCase(isDarkTheme)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase().onFailure { e ->
                Timber.e(e)
            }
        }
    }
}