package com.example.mytask.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mytask.R
import com.example.mytask.adapters.DetailsAdapter
import com.example.mytask.databinding.ActivityDetailsBinding
import com.example.mytask.litsners.ActionLitsner
import com.example.mytask.models.DaysEntity
import com.example.mytask.models.DetailsModel
import com.example.mytask.preferences.Preferences
import com.example.mytask.viewModels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private var binding: ActivityDetailsBinding? = null
    private val mDetailsViewModel: DetailsViewModel by viewModels()
    private var mList: ArrayList<DetailsModel> = ArrayList()
    private var mAdapter: DetailsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        initViews()
    }

    private fun initViews() {
        createDetailsList()
        setupAdapter()
        binding?.tvEdit?.setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))
            finish()
        }
    }

    private fun setupAdapter() {
        mAdapter = DetailsAdapter(
            this, mList, mDetailsViewModel
        )
        binding?.rvDaysList?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
        Handler(Looper.getMainLooper())?.postDelayed(

            Runnable { mAdapter?.removeUnusedList() },
            100
        )
    }

    private fun createDetailsList() {
        mList.add(DetailsModel(0, value = "Sun"))
        mList.add(DetailsModel(1, value = "Mon"))
        mList.add(DetailsModel(2, value = "Tue"))
        mList.add(DetailsModel(3, value = "Wed"))
        mList.add(DetailsModel(4, value = "Thu"))
        mList.add(DetailsModel(5, value = "Fri"))
        mList.add(DetailsModel(6, value = "Sat"))

    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding?.lifecycleOwner = this
    }

}