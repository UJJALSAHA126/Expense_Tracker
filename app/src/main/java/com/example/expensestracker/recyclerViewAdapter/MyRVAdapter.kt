package com.example.expensestracker.recyclerViewAdapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.MainActivity
import com.example.expensestracker.R
import com.example.expensestracker.constants.Converters.Companion.getDate
import com.example.expensestracker.data.MyData
import com.example.expensestracker.databinding.RvLayoutBinding
import com.example.expensestracker.recyclerViewAdapter.diffUtil.MyDiffUtil

class MyRVAdapter(private val activity: MainActivity) :
    RecyclerView.Adapter<MyRVAdapter.MYRVViewHolder>() {

    private var listOfRecord = ArrayList<MyData>()
    private val myDiffUtil = MyDiffUtil()

    inner class MYRVViewHolder(val binding: RvLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYRVViewHolder {
        return MYRVViewHolder(RvLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MYRVViewHolder, position: Int) {
        val binding = holder.binding
        val record = listOfRecord[position]
        bindData(binding, record, position + 1)

    }

    private fun bindData(binding: RvLayoutBinding, record: MyData, pos: Int) {
        val dateTime = getDate(record.time)

        binding.rootLay.setBackgroundResource(if (record.isIncome) R.drawable.round_corner_green else R.drawable.round_corner_red)

        binding.light.setBackgroundResource(if (record.isIncome) R.drawable.green_circle else R.drawable.red_circle)
        binding.recordNoTxt.text = pos.toString()
        binding.incomeOrNotTxt.text = if (record.isIncome) "Earned" else "Spent"
        binding.dateTxt.text = dateTime[0]
        binding.timeTxt.text = dateTime[1]

//        val amount = "${activity.resources.getText(R.string.Rs)} ${record.amount}"
        binding.amountTxt.text = record.amount.toString()

        val desc = if (record.description.length > 90) {
            record.description.substring(0, 86) + "..."
        } else record.description
        binding.descriptionTxt.text = desc

    }

    override fun getItemCount(): Int {
        return listOfRecord.size;
    }

    fun setDate(newList: ArrayList<MyData>) {
        myDiffUtil.setData(listOfRecord, newList)
        val results = DiffUtil.calculateDiff(myDiffUtil)
        this.listOfRecord.clear()
        this.listOfRecord.addAll(newList)
//        listOfRecord = newList
        notifyDataSetChanged()
//        results.dispatchUpdatesTo(this)
    }
}