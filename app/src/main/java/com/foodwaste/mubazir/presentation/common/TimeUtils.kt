package com.foodwaste.mubazir.presentation.common

import android.content.Context
import com.foodwaste.mubazir.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object TimeUtils {
    fun formatSelectedDate(date: Long): String {
        val timestamp = date * 1000
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun formatSelectedTime(time: Int): String {
        val hours = time / 3600
        val minutes = (time % 3600) / 60

        return String.format("%02d:%02d", hours, minutes)
    }

    fun getTimeAgo(context: Context, timestamp: Long?): String {
        if (timestamp == null) return ""
        val currentTime = System.currentTimeMillis() / 1000
        val timeDifference = currentTime - timestamp

        return when {
            timeDifference < 60 -> getTimeString(context, timeDifference, R.string.seconds_ago)
            timeDifference < 3600 -> {
                if (timeDifference < 120) {
                    getTimeString(context, 1, R.string.minute_ago)
                } else {
                    getTimeString(context, timeDifference / 60, R.string.minutes_ago)
                }
            }
            timeDifference < 86400 -> {
                if (timeDifference < 7200) {
                    getTimeString(context, 1, R.string.hour_ago)
                } else {
                    getTimeString(context, timeDifference / 3600, R.string.hours_ago)
                }
            }
            timeDifference < 604800 -> {
                if (timeDifference < 172800) {
                    getTimeString(context, 1, R.string.day_ago)
                } else {
                    getTimeString(context, timeDifference / 86400, R.string.days_ago)
                }
            }
            timeDifference < 2419200 -> {
                if (timeDifference < 1209600) {
                    getTimeString(context, 1, R.string.week_ago)
                } else {
                    getTimeString(context, timeDifference / 604800, R.string.weeks_ago)
                }
            }
            timeDifference < 29030400 -> {
                if (timeDifference < 4838400) {
                    getTimeString(context, 1, R.string.month_ago)
                } else {
                    getTimeString(context, timeDifference / 2419200, R.string.months_ago)
                }
            }
            else -> {
                if (timeDifference < 58060800) {
                    getTimeString(context, 1, R.string.year_ago)
                } else {
                    getTimeString(context, timeDifference / 29030400, R.string.years_ago)
                }
            }
        }
    }

    fun convertTimestampToString(timestamp: Long?): String {
        if(timestamp == null) return ""
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp * 1000 // konversi detik ke milidetik

        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun getTimeString(context: Context, timeValue: Long, resId: Int): String {
        return context.resources.getString(resId, timeValue)
    }
}