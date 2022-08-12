package com.example.expensestracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensestracker.constants.Converters.Companion.getDate
import com.example.expensestracker.data.MyData
import com.example.expensestracker.databinding.ActivityMainBinding
import com.example.expensestracker.recyclerViewAdapter.MyRVAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myRVAdapter: MyRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myRVAdapter = MyRVAdapter(this)
        binding.recordsRV.adapter = myRVAdapter
        binding.recordsRV.layoutManager = LinearLayoutManager(this)

        val rec1 = MyData(System.currentTimeMillis(),"",100,"Nothing",
            false,1000,900)
        val rec2 = MyData(System.currentTimeMillis()+60*60*1000,"",1000000,"Mummum",
            true,900,1000900)

        myRVAdapter.setDate(arrayListOf(rec1, rec2))
    }
}