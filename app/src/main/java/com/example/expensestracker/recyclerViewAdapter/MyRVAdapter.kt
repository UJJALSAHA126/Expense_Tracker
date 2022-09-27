package com.example.expensestracker.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.MainActivity
import com.example.expensestracker.R
import com.example.expensestracker.constants.Converters.Companion.getDate
import com.example.expensestracker.data.MyData
import com.example.expensestracker.databinding.RvLayoutBinding
import com.example.expensestracker.recyclerViewAdapter.diffUtil.MyDiffUtil

class MyRVAdapter(
    private val activity: MainActivity,
    private val recordDelete: (Int) -> Unit,
    private val updateRecord: (Int) -> Unit,
) :
    RecyclerView.Adapter<MyRVAdapter.MYRVViewHolder>() {

    private var listOfRecord = ArrayList<MyData>()
    var lastExpandedIndex = -1

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
        bindData(binding, record, position)
    }

    private fun bindData(binding: RvLayoutBinding, record: MyData, pos: Int) {
        val dateTime = getDate(record.time.toLong())

        binding.bottomLayout.visibility = if (record.isExpanded) View.VISIBLE else View.GONE

        binding.rootLay.setBackgroundResource(if (record.isIncome == 1) R.drawable.round_corner_green else R.drawable.round_corner_red)
        binding.light.setBackgroundResource(if (record.isIncome == 1) R.drawable.green_circle else R.drawable.red_circle)

        binding.incomeOrNotTxt.text = if (record.isIncome == 1) "Earned" else "Spent"
        binding.dateTxt.text = dateTime[0]
        binding.timeTxt.text = dateTime[1]
        binding.amountTxt.text = record.amount.toString()

        val desc = if (record.description.length > 20) {
            record.description.substring(0, 20) + "..."
        } else record.description

        binding.descriptionTxt.text = desc

        binding.root.setOnClickListener {
            println(pos)
            listOfRecord[pos].isExpanded = !listOfRecord[pos].isExpanded
            notifyItemChanged(pos)

            if (lastExpandedIndex != pos && lastExpandedIndex >= 0 && lastExpandedIndex < listOfRecord.size) {
                listOfRecord[lastExpandedIndex].isExpanded = false
                notifyItemChanged(lastExpandedIndex)
            }

            lastExpandedIndex = pos
        }

        binding.deleteRecord.setOnClickListener {
            recordDelete(pos)
        }

        binding.updateRecord.setOnClickListener {
            updateRecord(pos)
        }

    }

    override fun getItemCount(): Int {
        return listOfRecord.size;
    }

    fun setData(newList: ArrayList<MyData>) {
        val myDiffUtil = MyDiffUtil(listOfRecord, newList)
        val results = DiffUtil.calculateDiff(myDiffUtil)
        this.listOfRecord.clear()
        this.listOfRecord.addAll(newList)
        results.dispatchUpdatesTo(this)
    }

    fun getDataAtIndex(index: Int): MyData? {
        if (index < 0 || index >= listOfRecord.size)
            return null
        return listOfRecord[index]
    }

    fun addDataAtIndex(index: Int, data: MyData) {
        if (index < 0)
            return
        listOfRecord.add(index, data)
        notifyItemInserted(index)
    }

    fun removeDataFromIndex(index: Int) {
        if (index < 0 || index >= listOfRecord.size)
            return
        listOfRecord.removeAt(index)
        notifyItemRemoved(index)
    }

}