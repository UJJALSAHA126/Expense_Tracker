package com.example.expensestracker.constants

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    companion object {
        @SuppressLint("SimpleDateFormat")
        val formatterDate = SimpleDateFormat("dd/MM/yyyy")

        @SuppressLint("SimpleDateFormat")
        val formatterTime = SimpleDateFormat("hh:mm a")

        fun getDate(millis: Long): ArrayList<String> {
            val calender = Calendar.getInstance()
            calender.timeInMillis = millis

            val date = getFormattedDate(calender.time)
            val time = getFormattedTime(calender.time)

            return arrayListOf(date, time)
        }

        fun getFormattedDate(time: Date): String {
            return formatterDate.format(time)
        }

        fun getFormattedTime(time: Date): String {
            return formatterTime.format(time)
        }

        fun getMilliSecond(date: String): String {
            val dates = date.split("/")
            val calender = Calendar.getInstance()

            calender.set(dates[2].toInt(), dates[1].toInt() - 1, dates[0].toInt())
            return calender.timeInMillis.toString()
        }
    }
}