package com.example.expensestracker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensestracker.data.MyData
import com.example.expensestracker.databinding.ActivityMainBinding
import com.example.expensestracker.databinding.PopUpLayoutBinding
import com.example.expensestracker.myDataBase.MySQLiteDB
import com.example.expensestracker.popUpDialog.CustomDialog
import com.example.expensestracker.recyclerViewAdapter.MyRVAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myRVAdapter: MyRVAdapter

    private val myDB = MySQLiteDB(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myRVAdapter = MyRVAdapter(this)
        binding.recordsRV.adapter = myRVAdapter
        binding.recordsRV.layoutManager = LinearLayoutManager(this)

        loadAllData(myRVAdapter)

        binding.addRecordFAB.setOnClickListener {
            val customDialog = CustomDialog(this, this, null, { dialog, binding, time ->
                addRecord(binding, time)
                Toast.makeText(this, "Add Clicked", Toast.LENGTH_SHORT).show()
                loadAllData(myRVAdapter)
                dialog.stopLoading()
            }, {
                it.stopLoading()
            })
            customDialog.startLoading()
        }
    }

    private fun loadAllData(adapter: MyRVAdapter) {
        val allRecord = ArrayList<MyData>()
        val cursor = myDB.readAllData() ?: return
        println(cursor.count)
        while (cursor.moveToNext()) {
            val data = MyData(cursor)
            allRecord.add(data)
//            println(data)
        }
        myRVAdapter.setData(allRecord)
    }

    private fun addRecord(dialogBinding: PopUpLayoutBinding, time: String) {
        val cv = MyData.getContentValues(dialogBinding, time)
        cv?.also { myDB.addNewRecord(it) }
    }

//    private fun testingRV() {
//        val rec1 = MyData(System.currentTimeMillis(), "", 100, "Nothing",
//            0, 1000, 900)
//        val rec2 = MyData(System.currentTimeMillis() + 60 * 60 * 1000, "", 1000000, "Mummum",
//            0, 900, 1000900)
//
//        myRVAdapter.setData(arrayListOf(rec1))
//
//        Thread {
//            Thread.sleep(2500)
//            kotlin.run {
//                runOnUiThread {
//                    myRVAdapter.setData(arrayListOf(rec2, rec1))
//                    Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }.start()
//    }
}