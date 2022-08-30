package com.example.expensestracker.constants

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun getDate(millis: Long): ArrayList<String> {
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val formatterT = SimpleDateFormat("hh:mm a")
            val calender = Calendar.getInstance()
            calender.timeInMillis = millis
            val date = formatter.format(calender.time)
            val time = formatterT.format(calender.time)
            return arrayListOf(date, time)
        }

        fun getMilliSecond(date: String): String {
            val dates = date.split("/")
            val calender = Calendar.getInstance()
            for(d in dates) println(d.toInt())

            calender.set(dates[2].toInt(), dates[1].toInt() - 1, dates[0].toInt())
            return calender.timeInMillis.toString()
        }
    }
}