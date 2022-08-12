package com.example.expensestracker.data

import android.content.ContentValues
import com.example.expensestracker.constants.Constants.Companion.TABLE_NAME

class MyData {
    var time: Long = 0
    var name: String = ""
    var amount: Int = 0
    var description: String = ""
    var isIncome: Boolean = false
    var totalAmountBefore: Int = 0
    var totalAmountAfter: Int = 0

    constructor(
        time: Long,
        name: String,
        amount: Int,
        description: String,
        isIncome: Boolean,
        totalAmountBefore: Int,
        totalAmountAfter: Int,
    ) {
        this.time = time
        this.name = name
        this.amount = amount
        this.description = description
        this.isIncome = isIncome
        this.totalAmountBefore = totalAmountBefore
        this.totalAmountAfter = totalAmountAfter
    }


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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MyData

        if (time != other.time) return false
        if (name != other.name) return false
        if (amount != other.amount) return false
        if (description != other.description) return false
        if (isIncome != other.isIncome) return false
        if (totalAmountBefore != other.totalAmountBefore) return false
        if (totalAmountAfter != other.totalAmountAfter) return false

        return true
    }

    override fun hashCode(): Int {
        var result = time.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + amount
        result = 31 * result + description.hashCode()
        result = 31 * result + isIncome.hashCode()
        result = 31 * result + totalAmountBefore
        result = 31 * result + totalAmountAfter
        return result
    }
}