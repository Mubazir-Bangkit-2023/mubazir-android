package com.foodwaste.mubazir.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodwaste.mubazir.domain.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase().onFailure { e ->
                Timber.e(e)
            }
        }
    }
}