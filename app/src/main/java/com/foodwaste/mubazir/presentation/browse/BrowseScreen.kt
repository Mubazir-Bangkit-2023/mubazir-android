package com.foodwaste.mubazir.presentation.browse

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodwaste.mubazir.presentation.browse.component.FilterBottomSheet
import com.foodwaste.mubazir.presentation.browse.component.FilterRadiusOption
import com.foodwaste.mubazir.presentation.browse.component.SearchBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseScreen(
    navController: NavHostController
) {
    //search bar
    var query by remember { mutableStateOf("") }

    //bottom sheet
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    //filter
    var filterRadius by rememberSaveable {
        mutableStateOf("")
    }
    var filterMaxPrice by rememberSaveable {
        mutableStateOf("")
    }
    var isFreeFilterSelected by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedRadius by remember { mutableStateOf<FilterRadiusOption?>(null) }

    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ) {
        item {
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onFilterClick = {
                    openBottomSheet = !openBottomSheet
                }
            )
        }
    }

    if (openBottomSheet) {
        FilterBottomSheet(
            onDismiss = { openBottomSheet = false },
            onReset = {
                filterRadius = ""
                filterMaxPrice = ""
                isFreeFilterSelected = false
                selectedRadius = null
            },
            filterRadius = filterRadius,
            onRadiusChange = { filterRadius = it },
            selectedRadius = selectedRadius,
            onFilterRadiusSelected = {
                selectedRadius = it
                filterRadius = it.distance
            },
            filterMaxPrice = filterMaxPrice,
            onMaxPriceChange = { filterMaxPrice = it },
            isFreeFilterSelected = isFreeFilterSelected,
            onFreeFilterClick = {
                isFreeFilterSelected = !isFreeFilterSelected
                filterMaxPrice = if (isFreeFilterSelected) "0" else ""
            },
            onApplyClick = {
                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        openBottomSheet = false
                    }
                }
            }
        )
    }
}


