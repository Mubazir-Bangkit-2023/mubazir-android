package com.foodwaste.mubazir.presentation.common

import android.content.Context
import android.location.Address
import android.location.Geocoder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

object LocationUtils {
    fun distanceFormat(distance: Int): String {
        return when {
            distance < 1000 -> "$distance M"
            else -> {
                val distanceKm = distance / 1000.0
                val formattedDistance = String.format("%.1f", distanceKm)
                "$formattedDistance KM"
            }
        }
    }

    suspend fun getAddressFromLocation(
        latitude: Double?,
        longitude: Double?,
        context: Context,
        dispatcher: CoroutineDispatcher
    ): String {
        if (latitude == null || longitude == null) return ""

        return withContext(dispatcher) {
            val geocoder = Geocoder(context)
            var kelurahan = ""
            var kota = ""

            try {
                @Suppress("DEPRECATION") val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)

                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]

                    kelurahan = address.subLocality ?: ""
                    kota = address.subAdminArea ?: ""
                }
            } catch (e: Exception) {
                Timber.e(e)
            }

            val result = buildString {
                if (kelurahan.isNotBlank()) {
                    append(kelurahan)
                }

                if (kota.isNotBlank()) {
                    if (isNotBlank()) {
                        append(", ")
                    }
                    append(kota)
                }
            }

            result
        }
    }

//    suspend fun getAddressFromLocation(
//        latitude: Double?,
//        longitude: Double?,
//        context: Context,
//        dispatcher: CoroutineDispatcher
//    ): String {
//        if (latitude == null || longitude == null) return ""
//        return withContext(dispatcher) {
//            val geocoder = Geocoder(context)
//            var kelurahan = ""
//            var kota = ""
//
//            try {
//                val addresses: MutableList<Address>? =
//                    geocoder.getFromLocation(latitude, longitude, 1)
//
//                if (addresses != null) {
//                    if (addresses.isNotEmpty()) {
//                        val address = addresses[0]
//                        kelurahan = address.subLocality
//                        kota = address.subAdminArea
//                    }
//                }
//            } catch (e: Exception) {
//                Timber.e(e)
//            }
//
//            "$kelurahan, $kota"
//        }
//    }
}