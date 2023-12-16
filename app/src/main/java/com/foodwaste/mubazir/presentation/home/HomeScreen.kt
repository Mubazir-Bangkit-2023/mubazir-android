package com.foodwaste.mubazir.presentation.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.presentation.home.component.ImageSlider
import com.foodwaste.mubazir.presentation.home.component.RecommendationCard
import timber.log.Timber

@Composable
fun HomeScreen(
    navController: NavHostController
) {

    val requestPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = {
        Timber.d("Permission granted: $it")
    })

    LaunchedEffect(Unit){
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    HomeScreen(
        onClickCard = {
            navController.navigate("detail/$it")
        }
    )
}


@Composable
fun HomeScreen(
    onClickCard: (id: String) -> Unit,
) {
    val images = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3,
    )


    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            ImageSlider(images = images)
        }

        Spacer(modifier = Modifier.height(20.dp))

        //Nearby recommendation
        Text(
            text = stringResource(id = R.string.text_nearby_surplus_food),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )

        LazyRow(contentPadding = PaddingValues(10.dp)) {
            items(dummyData) {
                RecommendationCard(
                    onClickCard = {
                        onClickCard("mubazirId")
                    },
                    category = it.category,
                    title = it.title,
                    distance = it.distance,
                    price = it.price,
                    imageUrl = it.imageUrl
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        //Nearby restaurant food recommendation
        Text(
            text = stringResource(id = R.string.text_nearby_restaurant_food),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )

        LazyRow(contentPadding = PaddingValues(10.dp)) {
            items(dummyData1) {
                RecommendationCard(
                    onClickCard = {
                        onClickCard("mubazirId")
                    },
                    category = it.category,
                    title = it.title,
                    distance = it.distance,
                    price = it.price,
                    imageUrl = it.imageUrl
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        //Nearby home food
        Text(
            text = stringResource(id = R.string.text_nearby_home_food),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )

        LazyRow(contentPadding = PaddingValues(10.dp)) {
            items(dummyData2) {
                RecommendationCard(
                    onClickCard = {
                        onClickCard("mubazirId")
                    },
                    category = it.category,
                    title = it.title,
                    distance = it.distance,
                    price = it.price,
                    imageUrl = it.imageUrl
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        //Nearby Food Ingredients
        Text(
            text = stringResource(id = R.string.text_nearby_food_ingredients),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )

        LazyRow(contentPadding = PaddingValues(10.dp)) {
            items(dummyData3) {
                RecommendationCard(
                    onClickCard = {
                        onClickCard("mubazirId")
                    },
                    category = it.category,
                    title = it.title,
                    distance = it.distance,
                    price = it.price,
                    imageUrl = it.imageUrl
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}
