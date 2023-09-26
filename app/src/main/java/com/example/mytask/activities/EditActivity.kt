package com.example.mytask.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.mytask.R
import com.example.mytask.adapters.DetailsAdapter
import com.example.mytask.adapters.EditAdapter
import com.example.mytask.databinding.ActivityDetailsBinding
import com.example.mytask.databinding.ActivityEditBinding
import com.example.mytask.litsners.ActionLitsner
import com.example.mytask.litsners.onDayPosition
import com.example.mytask.models.DaysEntity
import com.example.mytask.models.DetailsModel
import com.example.mytask.viewModels.DetailsViewModel
import com.example.mytask.viewModels.EditActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditActivity : AppCompatActivity(), onDayPosition, ActionLitsner {
    private var binding: ActivityEditBinding? = null
    private val mEditViewModel: EditActivityViewModel by viewModels()
    private var mList: ArrayList<DetailsModel> = ArrayList()
    private var mAdapter: EditAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        initViews()
    }

    private fun initViews() {
        createDetailsList()
        setupAdapter()
    }

    private fun setupAdapter() {
        mAdapter = EditAdapter(
            this, mList, this, this
        )
        binding?.rvEdit?.adapter = mAdapter
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit)
        binding?.lifecycleOwner = this
    }

    override fun onDaySelected(position: Int): MutableList<DaysEntity> {
        return mEditViewModel.getDatabyDays(position)
    }

    override fun onDayDeleted(dayId: Int) {
        mEditViewModel.removeDayFromDb(dayId)
        mAdapter?.removeUnusedList()
        mAdapter?.notifyDataSetChanged()
    }
}