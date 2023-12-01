package com.foodwaste.mubazir.presentation.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.foodwaste.mubazir.R
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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate("maps")
                    },
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Filled.Map, contentDescription = "Maps View")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = stringResource(id = R.string.text_maps_view))
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = {
                        navController.navigate("add_post")
                    },
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Filled.PostAdd, contentDescription = "Add post")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = stringResource(id = R.string.text_add_post))
                }
            }
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

@Preview
@Composable
fun BrowseScreenPreview() {
    BrowseScreen(navController = rememberNavController())
}