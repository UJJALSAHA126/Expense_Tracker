package com.example.expensestracker.constants

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun getDate(millis: Long): String {
            val formatter = SimpleDateFormat("dd/mm/yyyy")
            val calender = Calendar.getInstance()
            calender.timeInMillis = millis
            return formatter.format(calender.time)
        }
    }
}