package com.example.expensestracker.recyclerViewAdapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.expensestracker.data.MyData

class MyDiffUtil(private val oldList: ArrayList<MyData>, private val newList: ArrayList<MyData>) : DiffUtil.Callback() {
//    private var oldList = ArrayList<MyData>()
//    private var newList = ArrayList<MyData>()

//    fun setData(oldL: ArrayList<MyData>, newL: ArrayList<MyData>) {
//        oldList = oldL
//        newList = newL
//    }

    override fun getOldListSize(): Int {
        return oldList.size;
    }

    override fun getNewListSize(): Int {
        return newList.size;
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].time == newList[newItemPosition].time;
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition];
    }
}