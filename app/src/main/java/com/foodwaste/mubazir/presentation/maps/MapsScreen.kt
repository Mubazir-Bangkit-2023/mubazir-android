package com.foodwaste.mubazir.presentation.maps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.foodwaste.mubazir.presentation.maps.component.SpotMarker
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import timber.log.Timber

@Composable
fun MapsScreen(
    navController: NavHostController,
    viewModel: MapsViewModel = hiltViewModel()
) {
    val uiSettings = remember {
        MapUiSettings(
            myLocationButtonEnabled = true,
            zoomControlsEnabled = false,
        )
    }
    val context = LocalContext.current

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        uiSettings = uiSettings,
        cameraPositionState = CameraPositionState(
            CameraPosition(
                LatLng(-6.557065526346378, 106.79506636125055),
                15f,
                0f,
                0f,
            )
        )
    ) {

        viewModel.foodSpotList.collectAsState().value.forEach {
            SpotMarker(
                title = it.title,
                lat = it.lat,
                lon = it.lon,
                categoryId = it.categoryId,
                price = it.price,
                image = it.image,
                onInfoWindowClick = {
                    navController.navigate("detail/${it.id}")
                }
            )
        }
    }
}