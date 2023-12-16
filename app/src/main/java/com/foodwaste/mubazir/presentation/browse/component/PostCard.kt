package com.foodwaste.mubazir.presentation.browse.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Badge
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.presentation.common.LocationUtils
import com.foodwaste.mubazir.presentation.common.PriceUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(
    onClickCard: () -> Unit,
    category : Int,
    title: String,
    distance: Int,
    price: Int,
    imageUrl: String
) {

    ElevatedCard(
        onClick = onClickCard,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.TopStart
        ) {

            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Badge(
                modifier = Modifier.
                padding(8.dp),
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(4.dp)
                ) {
                    when(category) {
                        1 -> Icon(
                            imageVector = Icons.Default.RestaurantMenu,
                            contentDescription = "icon restaurant",
                            modifier = Modifier.size(14.dp)
                        )
                        2 -> Icon(
                            imageVector = Icons.Default.FoodBank,
                            contentDescription = "icon home food",
                            modifier = Modifier.size(14.dp)
                        )
                        3 -> Icon(
                            painter = painterResource(id = R.drawable.ic_ingredient),
                            contentDescription = "icon ingredient",
                            modifier = Modifier.size(14.dp)
                        )
                    }

                    Text(
                        text = when(category) {
                            1 -> stringResource(id = R.string.text_restaurant_category)
                            2 -> stringResource(id = R.string.text_home_food_category)
                            3 -> stringResource(id = R.string.text_ingredient_category)
                            else -> ""
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.LocationOn, contentDescription = "Place", modifier = Modifier.size(22.dp))
                Text(text = LocationUtils.distanceFormat(distance), fontSize = 14.sp)
                }
                Text(text = if(price == 0) stringResource(id = R.string.text_free) else PriceUtils.toRupiah(price), fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            }
        }
    }

}

//@Preview
//@Composable
//fun RecommendationCardPreview() {
//    RecommendationCard(
//        onClickCard = {},
//        category = 1,
//        title = "Donut",
//        distance = 1,
//        price = 10000
//    )
//}