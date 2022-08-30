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
            val date = formatterDate.format(calender.time)
            val time = formatterTime.format(calender.time)
            return arrayListOf(date, time)
        }

        fun getFormattedDate(time: Date): String {
            return formatterDate.format(time)
        }

        fun getMilliSecond(date: String): String {
            val dates = date.split("/")
            val calender = Calendar.getInstance()
            for (d in dates) println(d.toInt())

            calender.set(dates[2].toInt(), dates[1].toInt() - 1, dates[0].toInt())
            return calender.timeInMillis.toString()
        }
    }
}