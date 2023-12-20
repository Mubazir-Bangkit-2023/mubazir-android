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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.domain.model.FoodPost
import com.foodwaste.mubazir.presentation.home.component.ImageSlider
import com.foodwaste.mubazir.presentation.home.component.RecommendationCard
import com.foodwaste.mubazir.presentation.home.component.ShimmerRecommendationCard
import com.foodwaste.mubazir.presentation.main.MainViewModel
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val location by mainViewModel.location.collectAsState()
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            Timber.d("Permission granted: $it")
            if (it) {
                mainViewModel.getCurrentLocation()
            }
        })

    LaunchedEffect(Unit) {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    LaunchedEffect(location) {
        homeViewModel.init()
    }

    HomeScreen(
        onClickCard = {
            navController.navigate("detail/$it")
        },
        loadingState = homeViewModel.loadingState,
        nearbyRecommendation = homeViewModel.nearbyRecommendation,
        nearbyRestaurantRecommendation = homeViewModel.nearbyRestaurantRecommendation,
        nearbyHomeFoodRecommendation = homeViewModel.nearbyHomeFoodRecommendation,
        nearbyFoodIngredientsRecommendation = homeViewModel.nearbyFoodIngredientsRecommendation,
    )
}


@Composable
fun HomeScreen(
    onClickCard: (id: String) -> Unit,
    loadingState: StateFlow<Boolean>,
    nearbyRecommendation: StateFlow<List<FoodPost>>,
    nearbyRestaurantRecommendation: StateFlow<List<FoodPost>>,
    nearbyHomeFoodRecommendation: StateFlow<List<FoodPost>>,
    nearbyFoodIngredientsRecommendation: StateFlow<List<FoodPost>>,

    ) {
    val images = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3,
    )

    val loading by loadingState.collectAsState()

    val nearbyList by nearbyRecommendation.collectAsState(initial = emptyList())
    val nearbyRestaurantList by nearbyRestaurantRecommendation.collectAsState(initial = emptyList())
    val nearbyHomeFoodList by nearbyHomeFoodRecommendation.collectAsState(initial = emptyList())
    val nearbyFoodIngredientsList by nearbyFoodIngredientsRecommendation.collectAsState(initial = emptyList())

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


        if (loading) {
            LazyRow(contentPadding = PaddingValues(10.dp)) {
                items(3) {
                    ShimmerRecommendationCard()
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        } else {
            LazyRow(contentPadding = PaddingValues(10.dp)) {
                items(nearbyList) {
                    RecommendationCard(
                        onClickCard = {
                            onClickCard(it.id)
                        },
                        category = it.categoryId,
                        title = it.title,
                        distance = it.distance,
                        price = it.price,
                        imageUrl = it.imageUrl
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
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

        if (loading) {
            LazyRow(contentPadding = PaddingValues(10.dp)) {
                items(3) {
                    ShimmerRecommendationCard()
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        } else {
            LazyRow(contentPadding = PaddingValues(10.dp)) {
                items(nearbyRestaurantList) {
                    RecommendationCard(
                        onClickCard = {
                            onClickCard(it.id)
                        },
                        category = it.categoryId,
                        title = it.title,
                        distance = it.distance,
                        price = it.price,
                        imageUrl = it.imageUrl
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
//        }

            Spacer(modifier = Modifier.height(20.dp))

            //Nearby home food
            Text(
                text = stringResource(id = R.string.text_nearby_home_food),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )

            LazyRow(contentPadding = PaddingValues(10.dp)) {
                items(nearbyHomeFoodList) {
                    RecommendationCard(
                        onClickCard = {
                            onClickCard(it.id)
                        },
                        category = it.categoryId,
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
                if (nearbyFoodIngredientsList.isNotEmpty()) {
                    items(nearbyFoodIngredientsList) {
                        RecommendationCard(
                            onClickCard = {
                                onClickCard(it.id)
                            },
                            category = it.categoryId,
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
    }
}
