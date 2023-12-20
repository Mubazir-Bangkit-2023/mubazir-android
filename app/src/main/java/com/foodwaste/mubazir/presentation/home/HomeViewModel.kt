package com.foodwaste.mubazir.presentation.home

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodwaste.mubazir.domain.model.FoodPost
import com.foodwaste.mubazir.domain.usecase.GetNearbyFoodIngredientsRecommendationUseCase
import com.foodwaste.mubazir.domain.usecase.GetNearbyHomeFoodRecommendationUseCase
import com.foodwaste.mubazir.domain.usecase.GetNearbyRecommendationUseCase
import com.foodwaste.mubazir.domain.usecase.GetNearbyRestaurantRecommendationUseCase
import com.foodwaste.mubazir.domain.usecase.GetStoredLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStoredLocationUseCase: GetStoredLocationUseCase,
    private val getNearbyRecommendationUseCase: GetNearbyRecommendationUseCase,
    private val getNearbyRestaurantRecommendationUseCase: GetNearbyRestaurantRecommendationUseCase,
    private val getNearbyHomeFoodRecommendationUseCase: GetNearbyHomeFoodRecommendationUseCase,
    private val getNearbyFoodIngredientsRecommendationUseCase: GetNearbyFoodIngredientsRecommendationUseCase
) : ViewModel() {

    private val _location = MutableStateFlow(Location(""))
    val location: StateFlow<Location>
        get() = _location.asStateFlow()

    private val _nearbyRecommendation = MutableStateFlow<List<FoodPost>>(emptyList())
    val nearbyRecommendation: StateFlow<List<FoodPost>>
        get() = _nearbyRecommendation.asStateFlow()

    private val _nearbyRestaurantRecommendation = MutableStateFlow<List<FoodPost>>(emptyList())
    val nearbyRestaurantRecommendation: StateFlow<List<FoodPost>>
        get() = _nearbyRestaurantRecommendation.asStateFlow()

    private val _nearbyHomeFoodRecommendation = MutableStateFlow<List<FoodPost>>(emptyList())
    val nearbyHomeFoodRecommendation: StateFlow<List<FoodPost>>
        get() = _nearbyHomeFoodRecommendation.asStateFlow()

    private val _nearbyFoodIngredientsRecommendation = MutableStateFlow<List<FoodPost>>(emptyList())
    val nearbyFoodIngredientsRecommendation: StateFlow<List<FoodPost>>
        get() = _nearbyFoodIngredientsRecommendation.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()



    init {
        init()
    }

    fun init(){
        viewModelScope.launch {
            getStoredLocationUseCase().collectLatest {
                if (it != null) {
                    _location.value = it
                }
            }
        }
        getNearbyRecommendation()
    }

    private fun getNearbyRecommendation() {
        viewModelScope.launch {
            _loadingState.value = true
            _nearbyRecommendation.value = getNearbyRecommendationUseCase(_location.value.latitude, _location.value.longitude)
            _nearbyRestaurantRecommendation.value = getNearbyRestaurantRecommendationUseCase(_location.value.latitude, _location.value.longitude)
            _nearbyHomeFoodRecommendation.value = getNearbyHomeFoodRecommendationUseCase(_location.value.latitude, _location.value.longitude)
            _nearbyFoodIngredientsRecommendation.value = getNearbyFoodIngredientsRecommendationUseCase(_location.value.latitude, _location.value.longitude)
            _loadingState.value = false
        }
    }

}