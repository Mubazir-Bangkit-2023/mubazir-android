package com.foodwaste.mubazir.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodwaste.mubazir.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dispatcher: CoroutineDispatcher,
    getUserUseCase: GetUserUseCase
) : ViewModel() {
    val loggedIn = getUserUseCase()
        .map { it != null }
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}