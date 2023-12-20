package com.foodwaste.mubazir.domain.usecase

import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GetCurrentLocationUseCase(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) {

        @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
        suspend operator fun invoke(): Result<Location> = suspendCancellableCoroutine {continuation ->
            val locationRequest =  CurrentLocationRequest.Builder().setPriority(Priority.PRIORITY_HIGH_ACCURACY).build()
            val locationTask: Task<Location> = fusedLocationProviderClient.getCurrentLocation(locationRequest, null)

            locationTask.addOnSuccessListener { location ->
                if(location != null ) {
                    continuation.resume(Result.success(location))
                } else {
                    continuation.resume(Result.failure(Exception("Location is null")))
                }
            }

            locationTask.addOnFailureListener { e ->
                continuation.resumeWithException(e)
            }

            continuation.invokeOnCancellation {
                if (!locationTask.isComplete) {
                    continuation.cancel()
                }
            }
        }
}