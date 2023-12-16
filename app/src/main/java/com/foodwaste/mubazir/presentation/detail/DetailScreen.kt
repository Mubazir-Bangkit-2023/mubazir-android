package com.foodwaste.mubazir.presentation.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.domain.model.FoodPostDetail
import com.foodwaste.mubazir.presentation.common.PriceUtils
import com.foodwaste.mubazir.presentation.common.TimeUtils
import com.foodwaste.mubazir.presentation.common.component.shimmerBrush
import com.foodwaste.mubazir.presentation.detail.component.Description
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun DetailScreen(
    navController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    DetailScreen(
        snackbarHostState = snackbarHostState,
        onBackPressed = {
            navController.popBackStack()
        },
        detailPostState = viewModel.detailPost,
        addressState = viewModel.addressState,
        loadingState = viewModel.loadingState
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    snackbarHostState: SnackbarHostState,
    onBackPressed: () -> Unit,
    detailPostState: StateFlow<FoodPostDetail?>,
    addressState: StateFlow<String>,
    loadingState: StateFlow<Boolean>,
) {
    var isTopBarTransparent by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val detailPost by detailPostState.collectAsState()

    val loading by loadingState.collectAsState()


    //Observe scroll state
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.value }
            .collect { scrollValue ->
                isTopBarTransparent = scrollValue <= 50
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier.clip(shape = CircleShape)
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (isTopBarTransparent) Color.Transparent else MaterialTheme.colorScheme.surface
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }

    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    var imgLoading by remember {
                        mutableStateOf(true)
                    }
                    AsyncImage(
                        model = detailPost?.imageUrl,
                        contentDescription = "Food image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(shimmerBrush(showShimmer = imgLoading))
                            .align(Alignment.Center),
                        onSuccess = {
                            imgLoading = false
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(13.dp, 20.dp)
                        .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {

                    //title
                    Column {
                        Text(
                            text = detailPost?.title ?: "",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .defaultMinSize(minWidth = 200.dp, minHeight = 15.dp)
                                .background(shimmerBrush(showShimmer = loading))
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = if (!loading) TimeUtils.getTimeAgo(
                                context,
                                detailPost?.createdAt
                            ) else "",
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .defaultMinSize(minWidth = 100.dp, minHeight = 10.dp)
                                .background(shimmerBrush(showShimmer = loading))
                        )
                    }

                    //Detail info
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = if (!loading) stringResource(id = R.string.text_detail) else "",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .defaultMinSize(minWidth = 50.dp, minHeight = 10.dp)
                                .background(shimmerBrush(showShimmer = loading))
                        )

                        Row {
                            Text(
                                text = if (!loading) stringResource(id = R.string.text_category) else "",
                                fontWeight = FontWeight.Light,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .weight(0.3f)
                                    .defaultMinSize(minWidth = 50.dp, minHeight = 10.dp)
                                    .background(shimmerBrush(showShimmer = loading))
                            )
                            Text(
                                text =
                                when (detailPost?.categoryId) {
                                    1 -> stringResource(id = R.string.text_restaurant_category)
                                    2 -> stringResource(id = R.string.text_home_food_category)
                                    3 -> stringResource(id = R.string.text_food_ingredient_category)
                                    else -> ""
                                },
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .weight(0.7f)
                                    .defaultMinSize(minWidth = 70.dp, minHeight = 10.dp)
                                    .background(shimmerBrush(showShimmer = loading))
                            )
                        }

                        val address by addressState.collectAsState()
                        if(address != "") {
                            Row {
                                Text(
                                    text = if (!loading) stringResource(id = R.string.text_location) else "",
                                    fontWeight = FontWeight.Light,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .weight(0.3f)
                                        .defaultMinSize(minWidth = 50.dp, minHeight = 10.dp)
                                        .background(shimmerBrush(showShimmer = loading))
                                )

                                Text(
                                    text = address,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .weight(0.7f)
                                        .defaultMinSize(minWidth = 70.dp, minHeight = 10.dp)
                                        .background(shimmerBrush(showShimmer = loading))
                                )
                            }
                        }

                        Row {
                            Text(
                                text = if (!loading) stringResource(id = R.string.text_pickup_time) else "",
                                fontWeight = FontWeight.Light,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .weight(0.3f)
                                    .defaultMinSize(minWidth = 50.dp, minHeight = 10.dp)
                                    .background(shimmerBrush(showShimmer = loading))
                            )
                            Timber.d("DetailPost: ${detailPost?.pickupTime}")
                            Text(
                                text = TimeUtils.convertTimestampToString(
                                    detailPost?.pickupTime
                                ),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .weight(0.7f)
                                    .defaultMinSize(minWidth = 70.dp, minHeight = 10.dp)
                                    .background(shimmerBrush(showShimmer = loading))
                            )
                        }
                        if (detailPost?.freshness != null && detailPost?.freshness != "" && detailPost?.categoryId == 3) {
                            Row {
                                Text(
                                    text = if (!loading) stringResource(id = R.string.text_food_freshness) else "",
                                    fontWeight = FontWeight.Light,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .weight(0.3f)
                                        .defaultMinSize(minWidth = 50.dp, minHeight = 10.dp)
                                        .background(shimmerBrush(showShimmer = loading))
                                )
                                Text(
                                    text = detailPost?.freshness ?: "",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .weight(0.7f)
                                        .defaultMinSize(minWidth = 70.dp, minHeight = 10.dp)
                                        .background(shimmerBrush(showShimmer = loading))
                                )
                            }
                        }
                    }

                    //Description
                    Column {
                        Text(
                            text = if (!loading) stringResource(id = R.string.text_description) else "",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        if (loading) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .background(shimmerBrush(showShimmer = true))
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .background(shimmerBrush(showShimmer = true))
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .background(shimmerBrush(showShimmer = true))
                            )
                        } else {
                            Description(description = detailPost?.description ?: "")
                        }
                    }

                    //Profile
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(shimmerBrush(showShimmer = loading)),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (!loading) {
                            Row {
                                if (detailPost?.user?.photo != null) {
                                    AsyncImage(
                                        model = detailPost?.user?.photo,
                                        contentDescription = "user picture",
                                        modifier = Modifier
                                            .size(40.dp)

                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_user),
                                        contentDescription = "user picture",
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = detailPost?.user?.name ?: "",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .defaultMinSize(minWidth = 40.dp, minHeight = 10.dp)

                                )
                            }
                            IconButton(
                                onClick = {
                                    val url = "https://wa.me/+62${detailPost?.user?.noHp}"
                                    val i = Intent(Intent.ACTION_VIEW)
                                    i.data = Uri.parse(url)
                                    context.startActivity(i)
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ),

                            ) {
                                if (!loading) {
                                    Icon(
                                        imageVector = Icons.Outlined.Whatsapp,
                                        contentDescription = "whatsapp",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }

                        }
                    }

                    Spacer(modifier = Modifier.height(70.dp))

                }
            }

            //Bottom bar
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 20.dp

            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .padding(13.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if(!loading) PriceUtils.toRupiah(detailPost?.price) else "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.defaultMinSize(minWidth = 50.dp, minHeight = 20.dp)
                    .background(shimmerBrush(showShimmer = loading))
                    )
                    Button(onClick = {
                        val gmmIntentUri =
                            Uri.parse("geo:${detailPost?.lat},${detailPost?.lon}?q=${detailPost?.lat},${detailPost?.lon}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        context.startActivity(mapIntent)
                    }, shape = RoundedCornerShape(30.dp), enabled = !loading) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Location",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Icon(
                                imageVector = Icons.Filled.Place,
                                contentDescription = "Location",
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }

            }
        }
    }
}

