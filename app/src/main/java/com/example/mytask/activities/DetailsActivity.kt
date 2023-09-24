package com.example.mytask.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mytask.R
import com.example.mytask.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private var binding: ActivityDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding?.lifecycleOwner = this
    }
}