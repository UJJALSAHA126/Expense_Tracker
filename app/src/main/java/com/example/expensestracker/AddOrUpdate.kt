package com.example.expensestracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.expensestracker.databinding.ActivityAddOrUpdateBinding

class AddOrUpdate : AppCompatActivity() {
    private lateinit var binding: ActivityAddOrUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}