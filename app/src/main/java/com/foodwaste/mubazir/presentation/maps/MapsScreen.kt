package com.foodwaste.mubazir.presentation.maps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.domain.model.FoodPostMarker
import com.foodwaste.mubazir.presentation.maps.component.SpotMarker
import com.foodwaste.mubazir.presentation.maps.component.bitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState


@Composable
fun MapsScreen(
    navController: NavHostController,
    viewModel: MapsViewModel = hiltViewModel()
) {
    val foodSpotList by viewModel.foodSpotList.collectAsState()
    val location by viewModel.location.collectAsState()
    MapsScreen(
        foodSpotList = foodSpotList,
        onInfoWindowClick = {
            navController.navigate("detail/${it}")
        },
        cameraPosition = CameraPosition(
            LatLng(location.latitude, location.longitude),
            15f,
            0f,
            0f,
        ),
        myLocation = LatLng(location.latitude, location.longitude)
    )
}


@Composable
fun MapsScreen(
    foodSpotList: List<FoodPostMarker>,
    onInfoWindowClick: (String) -> Unit,
    cameraPosition: CameraPosition = CameraPosition(
        LatLng(-0.7893, 113.9213),
        5f,
        0f,
        0f,
    ),
    myLocation: LatLng
) {
    val uiSettings = remember {
        MapUiSettings(
            myLocationButtonEnabled = true,
            zoomControlsEnabled = false,
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        uiSettings = uiSettings,
        cameraPositionState = CameraPositionState(
            cameraPosition
        ),

    ) {

        Marker(
            state = MarkerState(myLocation),
            icon = bitmapDescriptor(LocalContext.current, R.drawable.mascot_marker),
            title = stringResource(id = R.string.text_you)
        )

        foodSpotList.forEach {
            SpotMarker(
                title = it.title,
                lat = it.lat,
                lon = it.lon,
                categoryId = it.categoryId,
                price = it.price,
                imageUrl = it.image,
                onInfoWindowClick = { onInfoWindowClick(it.id) }
            )
        }

    }
}


