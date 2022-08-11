package com.example.expensestracker.myDataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.expensestracker.constants.Constants.Companion.TABLE_NAME
import com.example.expensestracker.constants.Constants.Companion.VERSION
import com.example.expensestracker.data.MyData


class MySQLiteDB(private var context: Context) :
    SQLiteOpenHelper(context, TABLE_NAME, null, VERSION) {

    override fun onCreate(p0: SQLiteDatabase?) {
        val query =
            "CREATE TABLE $TABLE_NAME (TIME TEXT PRIMARY KEY, NAME TEXT, AMOUNT INTEGER," +
                    " ISINCOME INTEGER, TOTALAMOUNTBEFORE INTEGER, TOTALAMOUNTAFTER INTEGER);"
        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(p0)
    }

    public fun addNewRecord(data: MyData) {
//        val query = MyData.getAddingRecordQuery(data)
//        writableDatabase.execSQL(query)

        val cv = MyData.getContentValues(data)
        val results = writableDatabase.insert(TABLE_NAME, null, cv)
        if (results == -1L) {
            Toast.makeText(context, "Record Added Successfully", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(context, "Something Went Wrong !", Toast.LENGTH_SHORT).show()
    }
}