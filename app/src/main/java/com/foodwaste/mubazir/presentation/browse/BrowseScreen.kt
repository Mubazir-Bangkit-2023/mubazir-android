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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.foodwaste.mubazir.domain.model.FoodPost
import com.foodwaste.mubazir.presentation.browse.component.FilterBottomSheet
import com.foodwaste.mubazir.presentation.browse.component.PostCard
import com.foodwaste.mubazir.presentation.browse.component.SearchBar
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun BrowseScreen(
    navController: NavHostController,
    viewModel: BrowseViewModel = hiltViewModel()
) {
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
        refresh = viewModel::onRefresh,
        searchQuery = viewModel.searchQuery,
        onQueryChange = viewModel::onQueryChange,
        loadingState = viewModel.loadingState,
        onFilterReset = viewModel::onFilterReset,
        radiusFilterState = viewModel.radiusFilterState,
        onChangeRadiusFilter = viewModel::onChangeRadiusFilter,
        maxPriceFilterState = viewModel.maxPriceFilterState,
        onChangeMaxPriceFilter = viewModel::onChangeMaxPriceFilter,
        categoryFilterState = viewModel.categoryFilterState,
        onSelectCategoryFilter = viewModel::onSelectCategoryFilter,
        onSearch = viewModel::search,
        onApplyFilter = viewModel::applyFilter
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
    searchQuery: StateFlow<String>,
    onQueryChange: (String) -> Unit,
    loadingState: StateFlow<Boolean>,
    onFilterReset: () -> Unit,
    radiusFilterState: StateFlow<String>,
    onChangeRadiusFilter: (String) -> Unit,
    maxPriceFilterState: StateFlow<String>,
    onChangeMaxPriceFilter: (String) -> Unit,
    categoryFilterState: StateFlow<String>,
    onSelectCategoryFilter: (String) -> Unit,
    onSearch : (String) -> Unit,
    onApplyFilter: () -> Unit,
) {
    //search bar
    val query by searchQuery.collectAsState()

    //bottom sheet
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val isRefresh by isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefresh, onRefresh = refresh)


    Box(modifier = Modifier
        .fillMaxSize()
        .pullRefresh(pullRefreshState)
    ) {
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
                        onQueryChange = onQueryChange,
                        onFilterClick = {
                            openBottomSheet = !openBottomSheet
                        },
                        onSearch = onSearch,
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
            onFilterReset = onFilterReset,
            radiusFilterState = radiusFilterState,
            onChangeRadiusFilter = onChangeRadiusFilter,
            maxPriceFilterState = maxPriceFilterState,
            onChangeMaxPriceFilter = onChangeMaxPriceFilter,
            categoryFilterState = categoryFilterState,
            onSelectCategoryFilter = onSelectCategoryFilter,
            onApplyClick = {
                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        openBottomSheet = false
                    }
                }
                onApplyFilter()
            }
        )
    }
}

@Preview
@Composable
fun BrowseScreenPreview() {
    BrowseScreen(navController = rememberNavController())
}