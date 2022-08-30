package com.example.expensestracker.popUpDialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.example.expensestracker.data.MyData
import com.example.expensestracker.databinding.PopUpLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

class CustomDialog(
    private val activity: Activity,
    private val context: Context,
    private val data: MyData? = null,
    private val addClicked: (CustomDialog, PopUpLayoutBinding, String) -> Unit,
    private val cancelClicked: (CustomDialog) -> Unit,
) {

    private var dialog: Dialog? = null

    @SuppressLint("SimpleDateFormat")
    val formatter = SimpleDateFormat("dd/MM/yyyy")

    @SuppressLint("SimpleDateFormat")
    val formatterT = SimpleDateFormat("hh:mm a")

    fun startLoading() {
        dialog = Dialog(context)
        val binding =
            PopUpLayoutBinding.inflate(LayoutInflater.from(context), null, false)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        dialog?.apply {
            setContentView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
            show()
        }

        binding.dateTxt.text = formatter.format(c.time)
        binding.timeTxt.text = formatterT.format(c.time)
        binding.earnedBtn.isChecked = true

        data?.let {
            "Update".also { binding.addUpdateBtn.text = it }
            binding.amountTxt.setText(data.amount)
            binding.descTxt.setText(data.description)
        }

        binding.dateTxt.setOnClickListener {
            val picker = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                c.set(y, m, d)
                binding.dateTxt.text = formatter.format(c.time)
            }
            val dialog = DatePickerDialog(context, picker, year, month, day)
            dialog.show()
        }

        binding.timeTxt.setOnClickListener {
            val timePicker = TimePickerDialog.OnTimeSetListener { _, t, t2 ->
                c.set(Calendar.HOUR_OF_DAY, t)
                c.set(Calendar.MINUTE, t2)
                binding.timeTxt.text = formatterT.format(c.time)
            }
            val dialog = TimePickerDialog(context, timePicker, hour, minute, false)
            dialog.show()
        }

        binding.addUpdateBtn.setOnClickListener {
            addClicked(this, binding, c.timeInMillis.toString())
        }
        binding.cancelBtn.setOnClickListener {
            cancelClicked(this)
        }
    }

    fun stopLoading() {
        dialog?.dismiss()
    }
}