package com.foodwaste.mubazir.presentation.myfoodpost

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.domain.model.FoodPostDetail
import com.foodwaste.mubazir.presentation.common.TimeUtils
import com.foodwaste.mubazir.presentation.myfoodpost.component.CardView
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyFoodPostScreen(
    navController: NavHostController,
    viewModel: MyFoodPostViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    MyFoodPostScreen(
        snackbarHostState = snackbarHostState,
        onClickBack = {
            navController.popBackStack()
        },
        onClickCard = {
            navController.navigate("detail/$it")
        },
        foodPostList = viewModel.postList,
        deletePost = viewModel::deletePost
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFoodPostScreen(
    snackbarHostState: SnackbarHostState,
    onClickBack: () -> Unit = {},
    onClickCard: (String) -> Unit,
    foodPostList: StateFlow<List<FoodPostDetail>>,
    deletePost: (String) -> Unit
) {
    val openDeleteDialog = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.text_my_food_post)) },
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        val posts by foodPostList.collectAsState()

        var postIdToDelete by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if(posts.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp, 10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {

                    items(posts) {
                        CardView(
                            modifier = Modifier.fillMaxWidth(),
                            openDeleteDialog = {
                                postIdToDelete = it.id
                                openDeleteDialog.value = true
                            },
                            onClick = {
                                onClickCard(it.id)
                            },
                            imageUrl = it.imageUrl,
                            title = it.title,
                            createdAt = TimeUtils.getTimeAgo(LocalContext.current, it.createdAt)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }

                }
            } else {
                Text(
                    text = stringResource(id = R.string.text_no_post),
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        if (openDeleteDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDeleteDialog.value = false
                },
                title = {
                    Text(stringResource(id = R.string.text_delete_post))
                },
                text = {
                    Text(text = stringResource(id = R.string.text_logout_confirmation))
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDeleteDialog.value = false
                            deletePost(postIdToDelete)
                        }
                    ) {
                        Text(
                            stringResource(id = R.string.text_delete),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDeleteDialog.value = false
                        }
                    ) {
                        Text(stringResource(id = R.string.text_cancel))
                    }
                }
            )
        }
    }
}
