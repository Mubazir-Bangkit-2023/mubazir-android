package com.foodwaste.mubazir.presentation.maps.component

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.presentation.common.PriceUtils
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState

@Composable
fun SpotMarker(
    title: String,
    lat: Double,
    lon: Double,
    categoryId: Int,
    price: Int,
    image: ImageBitmap?,
    onInfoWindowClick: () -> Unit
) {
    val context = LocalContext.current

    MarkerInfoWindow(
        state = MarkerState(LatLng(lat, lon)),
        icon = bitmapDescriptor(
            context, when (categoryId) {
                1 -> R.drawable.ic_restaurant_marker
                2 -> R.drawable.ic_homefood_marker
                3 -> R.drawable.ic_ingredient_marker
                else -> R.drawable.ic_restaurant_marker

            }
        ),
        onInfoWindowClick = {
            onInfoWindowClick()
        }
    ) {

        Surface(
            shape = RoundedCornerShape(10.dp),
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .width(200.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(5.dp))
                ) {
                    Image(
                        bitmap = image ?: ContextCompat.getDrawable(
                            context,
                            R.drawable.mubazir_icon
                        )!!
                            .toBitmap().asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )

                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = if (price == 0) stringResource(id = R.string.text_free) else PriceUtils.toRupiah(
                            price
                        ), fontSize = 15.sp
                    )
                }
            }
        }
    }
}


fun bitmapDescriptor(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}
