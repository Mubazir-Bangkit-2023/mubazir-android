package com.foodwaste.mubazir.presentation.home

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.presentation.home.component.ImageSlider
import com.foodwaste.mubazir.presentation.home.component.RecommendationCard

@Composable
fun HomeScreen(
    navController: NavHostController
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

        Text(
            text = stringResource(id = R.string.text_nearby_surplus_food),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )

        LazyRow(contentPadding = PaddingValues(10.dp)) {
            items(10) {
                RecommendationCard()
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.text_nearby_surplus_food),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )

        LazyRow(contentPadding = PaddingValues(10.dp)) {
            items(10) {
                RecommendationCard()
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.text_nearby_surplus_food),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )

        LazyRow(contentPadding = PaddingValues(10.dp)) {
            items(10) {
                RecommendationCard()
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Preview(device = Devices.PIXEL_4, showBackground = true, showSystemUi = true)
@Composable
fun HomeSCreenPreview() {
    HomeScreen(navController = rememberNavController())
}