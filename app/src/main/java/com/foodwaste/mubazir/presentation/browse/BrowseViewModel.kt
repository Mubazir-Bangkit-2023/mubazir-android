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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val _location = MutableStateFlow(Location(""))
    val location: StateFlow<Location>
        get() = _location.asStateFlow()

    //search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String>
        get() = _searchQuery.asStateFlow()

    fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

    //Filter radius
    private val _radiusFilterState = MutableStateFlow("")
    val radiusFilterState = _radiusFilterState.asStateFlow()

    fun onChangeRadiusFilter(radius: String) {
        _radiusFilterState.value = radius
    }

    //Filter max price
    private val _maxPriceFilterState = MutableStateFlow("")
    val maxPriceFilterState = _maxPriceFilterState.asStateFlow()

    fun onChangeMaxPriceFilter(maxPrice: String) {
        _maxPriceFilterState.value = maxPrice
    }

    //Filter selected category
    private val _categoryFilterState = MutableStateFlow("")
    val categoryFilterState = _categoryFilterState.asStateFlow()

    fun onSelectCategoryFilter(category: String) {
        _categoryFilterState.value = category
    }

    //FIlter reset
    fun onFilterReset() {
        _radiusFilterState.value = ""
        _maxPriceFilterState.value = ""
        _categoryFilterState.value = ""
        getData()
    }

    fun onRefresh() {
        _isRefreshing.value = true
        _radiusFilterState.value = ""
        _maxPriceFilterState.value = ""
        _categoryFilterState.value = ""
        _searchQuery.value = ""
        getData()
        _isRefreshing.value = false
    }


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


    private fun getData() {

        viewModelScope.launch {
            _loadingState.value = true
            browseUseCase.invoke(
                location.value.latitude,
                location.value.longitude,
                _searchQuery.value,
                _categoryFilterState.value,
                formatRadiusString(_radiusFilterState.value),
                _maxPriceFilterState.value
            )
                .cachedIn(viewModelScope)
                .collectLatest {
                    _foodPostList.value = it
                }
            _loadingState.value = false
        }
    }

    fun applyFilter() {
        getData()
    }

    fun search(s: String){
        _searchQuery.value = s
        getData()
    }

    private fun filterNumericInput(input: String): String {
        return input.filter { it.isDigit() }
    }

    private fun formatRadiusString(radius: String): String {
       if(radius == "") return ""
        val r = filterNumericInput(radius).toInt() * 1000
        return r.toString()
    }

}