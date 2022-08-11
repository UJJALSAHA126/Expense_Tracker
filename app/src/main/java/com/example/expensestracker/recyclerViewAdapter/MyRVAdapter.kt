package com.example.expensestracker.recyclerViewAdapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.data.MyData
import com.example.expensestracker.databinding.RvLayoutBinding

class MyRVAdapter : RecyclerView.Adapter<MyRVAdapter.MYRVViewHolder>() {
    private val listOfRecord = ArrayList<MyData>()

    inner class MYRVViewHolder(val binding: RvLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYRVViewHolder {
        return MYRVViewHolder(RvLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MYRVViewHolder, position: Int) {
        val binding = holder.binding
        if (listOfRecord[position].isIncome) {
            binding.light.setBackgroundColor(Color.GREEN)
        } else binding.light.setBackgroundColor(Color.RED)
    }

    override fun getItemCount(): Int {
        return listOfRecord.size;
    }
}