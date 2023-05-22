package com.example.codemaster.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class DateTimeConverter {

    fun convertCCTimeToLong(timeString: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        try {
            val date = dateFormat.parse(timeString)
            return date.time
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return -1L // Return a default value (-1) in case of an error
    }

    fun convertTimeStringToLong(timeString: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        return try {
            val date = dateFormat.parse(timeString)
            date?.time ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    fun convertStringToDate(utcTime: String) : String {
        val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")

        return try {
            val utcDate = utcFormat.parse(utcTime)

            val indianFormat = SimpleDateFormat("dd MMM, yyyy - hh:mm a", Locale.getDefault())
            indianFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")

            indianFormat.format(utcDate!!)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
}