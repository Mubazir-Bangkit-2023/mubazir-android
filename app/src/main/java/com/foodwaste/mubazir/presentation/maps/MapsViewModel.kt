package com.foodwaste.mubazir.presentation.maps

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodwaste.mubazir.domain.model.FoodPostMarker
import com.foodwaste.mubazir.presentation.maps.component.SpotMarker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _foodSpotList = MutableStateFlow(listOf<FoodPostMarker>())
    val foodSpotList = _foodSpotList

    init {
        getFoodSpot()
    }

    private fun getFoodSpot() {
        viewModelScope.launch(dispatcher) {
            val foodSpot = dummyDataMaps.map {
                val image = downloadAsBitmap(it.imageUrl)
                FoodPostMarker(
                    id = it.title,
                    title = it.title,
                    price = it.price,
                    categoryId = it.category,
                    lat = it.lat,
                    lon = it.lon,
                    image = image
                )
            }
            _foodSpotList.value = foodSpot
        }
    }

    private suspend fun downloadAsBitmap(
        url: String?
    ): ImageBitmap? {
        return withContext(dispatcher) {
            try {
                return@withContext BitmapFactory.decodeStream(URL(url).openStream()).asImageBitmap()
            } catch (e: IOException) {
                e.printStackTrace()
                return@withContext null
            } catch (e: MalformedURLException) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }
}

data class Recommendation(
    val category: Int,
    val title: String,
    val distance: Int,
    val lat: Double,
    val lon: Double,
    val price: Int,
    val imageUrl: String
)

var dummyDataMaps = listOf(
    Recommendation(
        category = 2,
        title = "Bolu Pukis Budi Agung",
        distance = 560,
        lat =  -6.561145567197359,
        lon = 106.79366801620192,
        price = 5000,
        imageUrl = "https://static.promediateknologi.id/crop/20x185:718x625/750x500/webp/photo/2023/08/07/kue-pukis-4258304942.jpg"
    ),
    Recommendation(
        category = 1,
        title = "Ayam Geprek Mas Budi",
        distance = 720,
        lat =  -6.55955743816361,
        lon = 106.79159198662191,
        price = 10000,
        imageUrl = "https://www.nestleprofessional.co.id/sites/default/files/srh_recipes/4845bc09ddd23ca5a27d75c3ea424db3.png"
    ),
    Recommendation(
        category = 3,
        title = "Pepaya mateng",
        distance = 1200,
        lat =  -6.562400806997023,
        lon = 106.79507400403511,
        price = 15000,
        imageUrl = "https://asset-a.grid.id/crop/0x0:0x0/x/photo/2022/05/12/pepayajpg-20220512080939.jpg"
    ),
    Recommendation(
        category = 1,
        title = "Nasi Goreng Ronaldo",
        distance = 1520,
        lat =  -6.5556327809245465,
        lon = 106.79518780061125,
        price = 8500,
        imageUrl = "https://assets-pergikuliner.com/5DQRl4RcX7HdqUFaFIW-rC6_exw=/fit-in/1366x768/smart/filters:no_upscale()/https://assets-pergikuliner.com/uploads/image/picture/2228193/picture-1621505010.jpg"
    ),
)