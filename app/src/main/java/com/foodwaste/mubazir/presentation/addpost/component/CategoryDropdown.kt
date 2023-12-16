package com.foodwaste.mubazir.presentation.addpost.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FoodBank
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.foodwaste.mubazir.R
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CategoryDropdown(
    categoryState: StateFlow<String>,
    onChangeCategory: (String, Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                color = if (!expanded) Color.Gray else MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { expanded = true },

        ) {
        val category by categoryState.collectAsState()
        Text(
            text = if (category != "") category else stringResource(id = R.string.text_category),
            color = if (!expanded) {
                if (category == "") Color.Gray else Color.Unspecified
            } else MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(17.dp)
                .align(Alignment.CenterStart)
        )
        Icon(
            imageVector = Icons.Outlined.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterEnd),
            tint = if (!expanded) Color.Gray else MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.942f)
        ) {
            val restaurantCategory = stringResource(id = R.string.text_restaurant_category)
            DropdownMenuItem(
                text = { Text(restaurantCategory) },
                onClick = {
                    onChangeCategory(restaurantCategory, 1)
                    expanded = false
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.RestaurantMenu,
                        contentDescription = null
                    )
                })
            val homeFoodCategory = stringResource(id = R.string.text_home_food_category)
            DropdownMenuItem(
                text = { Text(homeFoodCategory) },
                onClick = {
                    onChangeCategory(homeFoodCategory, 2)
                    expanded = false
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.FoodBank,
                        contentDescription = null
                    )
                })
            val foodIngredientCategory = stringResource(id = R.string.text_food_ingredient_category)
            DropdownMenuItem(
                text = { Text(foodIngredientCategory) },
                onClick = {
                    onChangeCategory(foodIngredientCategory, 3)
                    expanded = false
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_ingredient),
                        contentDescription = null
                    )
                })
        }
    }
}