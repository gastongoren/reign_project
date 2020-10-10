package com.example.reignproject.articlesPosted.view

import java.text.SimpleDateFormat
import java.util.*

class CurrentTime {
    fun getDifferenceTime(time: String): String {
        val userDob: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(time)
        val today = Date()
        val diff = today.time - userDob.time
        val numOfDays = (diff / (1000 * 60 * 60 * 24)).toInt()
        val hours = (diff / (1000 * 60 * 60)).toInt()
        val minutes = (diff / (10000 * 60)).toInt()

        return when {
            numOfDays > 1 -> {
                getDaysAgo(numOfDays)
            }
            hours > 1 -> {
                "$hours:$minutes h"

            }
            minutes in 1..59 -> {
                "$minutes m"
            }
            else -> {
                ""
            }
        }
    }

    private fun getDaysAgo(daysAgo: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        return calendar.time.toString()
    }
}