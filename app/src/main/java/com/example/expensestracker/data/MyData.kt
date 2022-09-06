package com.example.expensestracker.data

import android.content.ContentValues
import android.database.Cursor
import com.example.expensestracker.constants.Constants.Companion.TABLE_NAME
import com.example.expensestracker.databinding.PopUpLayoutBinding

class MyData {
    var time: String
    var amount: Int = 0
    var description: String = ""
    var isIncome: Int = 0
    var totalAmountBefore: Int = 0
    var totalAmountAfter: Int = 0

    constructor(
        time: String,
        amount: Int,
        description: String,
        isIncome: Int,
        totalAmountBefore: Int,
        totalAmountAfter: Int,
    ) {
        this.time = time
        this.amount = amount
        this.description = description
        this.isIncome = isIncome
        this.totalAmountBefore = totalAmountBefore
        this.totalAmountAfter = totalAmountAfter
    }

    constructor(cursor: Cursor) {
        cursor.apply {
            time = cursor.getString(0)
            description = cursor.getString(1)
            amount = cursor.getInt(2)
            isIncome = cursor.getInt(3)
        }
    }

    companion object {
        fun getTableCreationQuery(): String {
            val query =
                "CREATE TABLE $TABLE_NAME (TIME TEXT PRIMARY KEY, DETAILS TEXT, AMOUNT INTEGER," +
                        "  ISINCOME INTEGER, TOTALAMOUNTBEFORE INTEGER, TOTALAMOUNTAFTER INTEGER);"
            return query
        }

        fun getAddingRecordQuery(data: MyData): String {
            return "INSERT INTO $TABLE_NAME VALUES (${data.time}, ${data.amount}," +
                    "${data.description} ${data.isIncome}, ${data.totalAmountBefore}, ${data.totalAmountAfter});";
        }

        fun getContentValues(dialogBinding: PopUpLayoutBinding, time: String): ContentValues? {
            val data = getDataFromDialog(dialogBinding, time) ?: return null
            return getContentValues(data)
        }

        fun getContentValues(data: MyData): ContentValues {
            val cv = ContentValues()
            cv.put("TIME", data.time)
            cv.put("DETAILS", data.description)
            cv.put("AMOUNT", data.amount)
            cv.put("ISINCOME", data.isIncome)
            cv.put("TOTALAMOUNTBEFORE", data.totalAmountBefore)
            cv.put("TOTALAMOUNTAFTER", data.totalAmountAfter)

            return cv;
        }

        private fun getDataFromDialog(dialogBinding: PopUpLayoutBinding, time: String): MyData? {
            if (dialogBinding.amountTxt.text.isEmpty())
                return null
            val amount = dialogBinding.amountTxt.text.toString().toInt()

            val details =
                if (dialogBinding.descTxt.text.isEmpty()) " " else dialogBinding.descTxt.text.toString()

            val isIncome = if (dialogBinding.spentBtn.isChecked) 0 else 1
            return MyData(time, amount, details, isIncome, 0, 0)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MyData

        if (time != other.time) return false
        if (amount != other.amount) return false
        if (description != other.description) return false
        if (isIncome != other.isIncome) return false
        if (totalAmountBefore != other.totalAmountBefore) return false
        if (totalAmountAfter != other.totalAmountAfter) return false

        return true
    }

    override fun hashCode(): Int {
        var result = time.hashCode()
        result = 31 * result + amount
        result = 31 * result + description.hashCode()
        result = 31 * result + isIncome.hashCode()
        result = 31 * result + totalAmountBefore
        result = 31 * result + totalAmountAfter
        return result
    }

    override fun toString(): String {
        return "MyData(time='$time', amount=$amount, description='$description', isIncome=$isIncome, " +
                "totalAmountBefore=$totalAmountBefore, totalAmountAfter=$totalAmountAfter)"
    }
}