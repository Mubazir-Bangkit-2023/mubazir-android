package com.foodwaste.mubazir.presentation.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.foodwaste.mubazir.domain.model.FoodPost
import com.foodwaste.mubazir.presentation.browse.component.FilterBottomSheet
import com.foodwaste.mubazir.presentation.browse.component.FilterRadiusOption
import com.foodwaste.mubazir.presentation.browse.component.PostCard
import com.foodwaste.mubazir.presentation.browse.component.SearchBar
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun BrowseScreen(
    navController: NavHostController,
    viewModel: BrowseViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val foodPostList : LazyPagingItems<FoodPost> = viewModel.foodPostList.collectAsLazyPagingItems()

    BrowseScreen(
        onClickAddPost = {
            navController.navigate("add_post")
        },
        onClickCard = {
            navController.navigate("detail/$it")
        },
        foodPosts = foodPostList,
        isRefreshing = viewModel.isRefreshing,
        refresh = viewModel::getData
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BrowseScreen(
    onClickAddPost: () -> Unit,
    onClickCard: (id: String) -> Unit,
    foodPosts: LazyPagingItems<FoodPost>,
    isRefreshing: StateFlow<Boolean>,
    refresh: () -> Unit,
) {
    //search bar
    var query by remember { mutableStateOf("") }

    //bottom sheet
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val isRefresh by isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefresh, onRefresh = refresh)

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
    Box(modifier = Modifier
        .fillMaxSize()
        .pullRefresh(pullRefreshState)) {
        LazyColumn(
            contentPadding = PaddingValues(10.dp)
        ) {
            item {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp)
                        .height(50.dp)
                ) {
                    SearchBar(
                        query = query,
                        onQueryChange = { query = it },
                        onFilterClick = {
                            openBottomSheet = !openBottomSheet
                        },
                        modifier = Modifier.weight(0.8f)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Button(
                        onClick = onClickAddPost,
                        shape = RoundedCornerShape(30.dp),
                        modifier = Modifier
                            .weight(0.2f)
                            .padding(0.dp, 5.dp, 0.dp, 0.dp)
                            .height(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PostAdd,
                            contentDescription = "Add post",
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }


            items(foodPosts.itemCount) { index ->
                PostCard(
                    onClickCard = { onClickCard(foodPosts[index]!!.id) },
                    category = foodPosts[index]!!.categoryId,
                    title = foodPosts[index]!!.title,
                    distance = foodPosts[index]!!.distance,
                    price = foodPosts[index]!!.price,
                    imageUrl = foodPosts[index]!!.imageUrl,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

        }

        PullRefreshIndicator(refreshing = isRefresh, state = pullRefreshState,  modifier = Modifier.align(Alignment.TopCenter))
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