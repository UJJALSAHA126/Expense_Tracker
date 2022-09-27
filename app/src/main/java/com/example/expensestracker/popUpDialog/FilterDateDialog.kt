package com.example.expensestracker.popUpDialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensestracker.MainActivity
import com.example.expensestracker.constants.Converters
import com.example.expensestracker.data.MyData
import com.example.expensestracker.databinding.FilterDatePopUpLayoutBinding
import com.example.expensestracker.myDataBase.MySQLiteDB
import com.example.expensestracker.recyclerViewAdapter.MyRVAdapter
import java.util.*

class FilterDateDialog(private val context: Context, private val activity: MainActivity) {

    private var _binding: FilterDatePopUpLayoutBinding? = null
    private val binding get() = _binding!!

    private var dialog: Dialog? = null

    private val startDate: Calendar = Calendar.getInstance()
    private val endDate: Calendar = Calendar.getInstance()

    private lateinit var adapter: MyRVAdapter

    private var myDb = MySQLiteDB(context)

    fun startLoading() {
        dialog = Dialog(context)
        _binding =
            FilterDatePopUpLayoutBinding.inflate(LayoutInflater.from(context), null, false)
        if (_binding == null) return

        dialog?.apply {
            setContentView(binding.root)
            setCancelable(true)
            setCanceledOnTouchOutside(true)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
            show()
        }

        adapter = MyRVAdapter(activity, { _ -> }, { _ -> })
        binding.filteredRv.adapter = adapter
        binding.filteredRv.layoutManager = LinearLayoutManager(context)

        val currTime =
            Converters.getFormattedDate(startDate.time)

        binding.startDate.text = currTime
        binding.endDate.text = currTime

        val year = startDate.get(Calendar.YEAR)
        val month = startDate.get(Calendar.MONTH)
        val date = startDate.get(Calendar.DAY_OF_MONTH)

        startDate.set(year, month, date, 0, 0, 0)
        endDate.set(year, month, date, 23, 59, 59)

//        println("Triple Equal" + (statDate === endDate))
//        println("Double Equal" + (statDate == endDate))

        binding.startDate.setOnClickListener {
            val pickerListener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                startDate.set(y, m, d)
                binding.startDate.text = Converters.getFormattedDate(startDate.time)
            }
            DatePickerDialog(context, pickerListener, year, month, date)
                .show()
        }

        binding.endDate.setOnClickListener {
            val pickerListener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                endDate.set(y, m, d, 23, 59, 59)
                binding.endDate.text = Converters.getFormattedDate(endDate.time)
            }
            DatePickerDialog(context, pickerListener, year, month, date)
                .show()
        }

        binding.filterBtn.setOnClickListener {
            loadData()
        }

    }

    private fun loadData() {
        val cursor =
            myDb.getFilteredData(startDate.timeInMillis.toString(), endDate.timeInMillis.toString())
                ?: return
        val filteredData = ArrayList<MyData>()
        while (cursor.moveToNext()) {
            filteredData.add(MyData(cursor))
        }
        adapter.setData(filteredData)
    }

    fun stopLoading() {
        dialog?.also {
            it.dismiss()
            dialog = null
            _binding = null
        }
    }
}