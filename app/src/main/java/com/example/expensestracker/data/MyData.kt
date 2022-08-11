package com.example.expensestracker.data

import android.content.ContentValues
import com.example.expensestracker.constants.Constants.Companion.TABLE_NAME

class MyData {
    var time: Long = 0
    var name: String = ""
    var amount: Int = 0
    var details: String = ""
    var isIncome: Boolean = false
    var totalAmountBefore: Int = 0
    var totalAmountAfter: Int = 0

    companion object {
        fun getTableCreationQuery(): String {
            val query =
                "CREATE TABLE $TABLE_NAME (TIME TEXT PRIMARY KEY, NAME TEXT, AMOUNT INTEGER," +
                        " ISINCOME INTEGER, TOTALAMOUNTBEFORE INTEGER, TOTALAMOUNTAFTER INTEGER)"
            return query
        }

        fun getAddingRecordQuery(data: MyData): String {
            return " "
        }

        fun getContentValues(data: MyData): ContentValues {
            return ContentValues()
        }
    }
}