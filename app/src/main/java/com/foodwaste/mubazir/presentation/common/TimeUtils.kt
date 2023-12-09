package com.foodwaste.mubazir.presentation.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeUtils {
    fun formatSelectedDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun formatSelectedTime(time: Int): String {
        val hours = time / 3600
        val minutes = (time % 3600) / 60

        return String.format("%02d:%02d", hours, minutes)
    }
}