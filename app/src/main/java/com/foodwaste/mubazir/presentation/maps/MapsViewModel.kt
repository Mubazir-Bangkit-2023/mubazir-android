package com.foodwaste.mubazir.presentation.maps

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodwaste.mubazir.domain.model.FoodPostMarker
import com.foodwaste.mubazir.domain.usecase.GetFoodPostMapViewUseCase
import com.foodwaste.mubazir.domain.usecase.GetStoredLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val getFoodPostMapViewUseCase: GetFoodPostMapViewUseCase,
    private val getStoredLocationUseCase: GetStoredLocationUseCase
) : ViewModel() {

    private val _location = MutableStateFlow(Location(""))
    val location = _location

    private val _foodSpotList = MutableStateFlow(listOf<FoodPostMarker>())
    val foodSpotList = _foodSpotList



    init {
        viewModelScope.launch(dispatcher) {
            getStoredLocationUseCase().collect {
                if (it != null) {
                    _location.value = it
                }
            }
        }
        getMarkerList()
    }

    private fun getMarkerList(){
        viewModelScope.launch(dispatcher) {
            val list = getFoodPostMapViewUseCase(_location.value.latitude, _location.value.longitude).map { foodPost ->
                FoodPostMarker(
                    id = foodPost.id,
                    title = foodPost.title,
                    price = foodPost.price,
                    categoryId = foodPost.categoryId,
                    lat = foodPost.lat,
                    lon = foodPost.lon,
                    image = foodPost.imageUrl
                ) }
            _foodSpotList.value = list
        }
    }
}
