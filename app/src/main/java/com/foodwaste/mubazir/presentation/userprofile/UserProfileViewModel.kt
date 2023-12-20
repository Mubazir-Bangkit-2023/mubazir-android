package com.foodwaste.mubazir.presentation.userprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodwaste.mubazir.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    dispatcher: CoroutineDispatcher
): ViewModel(){
    val user = getUserUseCase().flowOn(dispatcher).stateIn(viewModelScope, SharingStarted.Lazily, null)
}