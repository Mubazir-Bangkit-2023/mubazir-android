package com.foodwaste.mubazir.presentation.browse

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.foodwaste.mubazir.domain.model.FoodPost
import com.foodwaste.mubazir.domain.usecase.BrowseUseCase
import com.foodwaste.mubazir.domain.usecase.GetStoredLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val browseUseCase: BrowseUseCase,
    private val getStoredLocationUseCase: GetStoredLocationUseCase
) : ViewModel() {
    private val _foodPostList = MutableStateFlow<PagingData<FoodPost>>(PagingData.empty())
    val foodPostList: StateFlow<PagingData<FoodPost>>
        get() = _foodPostList.asStateFlow()
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()
//
//    private val refreshTrigger = MutableStateFlow(Unit)

    private val _location = MutableStateFlow(Location(""))
    val location: StateFlow<Location>
        get() = _location.asStateFlow()

//    val location: StateFlow<Location>
//        get() {
//            val location = MutableStateFlow(Location(""))
//            viewModelScope.launch {
//                getStoredLocationUseCase().collectLatest {
//                    if (it != null) {
//                        location.value = it
//                    }
//                }
//            }
//            return location
//        }

//    init {
//        getData()
//    }

    init {
        viewModelScope.launch {
            getStoredLocationUseCase().collectLatest {
                if (it != null) {
                    _location.value = it
                }
            }
        }

        getData()
    }

//    val foodPostList = combine(location, refreshTrigger) { location, _ ->
//        browseUseCase.invoke(location.latitude, location.longitude)
//            .cachedIn(viewModelScope)
//    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

//    fun refreshData() {
//        refreshTrigger.value = Unit
//    }

    fun getData() {
        viewModelScope.launch {
            browseUseCase.invoke(location.value.latitude, location.value.longitude)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _foodPostList.value = it
                }
        }
    }
//    val foodPostList = browseUseCase.invoke(location.value.latitude,location.value.longitude).cachedIn(viewModelScope)

}