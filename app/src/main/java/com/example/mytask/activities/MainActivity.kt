package com.example.mytask.activities

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytask.R
import com.example.mytask.adapters.DaysAdapter
import com.example.mytask.databinding.ActivityMainBinding
import com.example.mytask.models.DaysEntity
import com.example.mytask.models.DaysModel
import com.example.mytask.preferences.Preferences
import com.example.mytask.utils.Constants
import com.example.mytask.utils.Utils
import com.example.mytask.viewModels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val mViewModel: MainActivityViewModel by viewModels()
    private var selectedStartTime: Long? = null
    private var selectedEndTime: Long? = null
    private var selectedStartTimeFormatted: String? = null
    private var selectedEndTimeFormatted: String? = null
    private var mAdapter: DaysAdapter? = null
    private var mList: ArrayList<DaysModel> = ArrayList()
    lateinit var mPreferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        initViews()
        setupObservers()
        setupClickEvents()
    }

    private fun setupClickEvents() {
        mViewModel?.startTimeClicked?.observe(this, Observer {
            showTimePickerDialog(true)
        })
        mViewModel?.endTimeClicked?.observe(this, Observer {
            showTimePickerDialog(false)
        })
        mViewModel?.continueButtonClicked?.observe(this, Observer {
            if (!Utils.isFromAdd) {
                mList.forEach {
                    if (mPreferences.getString(it.postion.toString())?.isNotBlank() == true) {
                        val times =
                            mPreferences.getString(it.postion.toString())?.split(",")
                                ?.toTypedArray()
                        val daysEntity = times?.get(0)
                            ?.let { it1 -> DaysEntity(0, it.postion, it1, times.get(1)) }
                        daysEntity?.let { it1 -> mViewModel?.insertDatatoDb(it1) }
                    }
                }
            } else {
                Utils.isFromAdd = false
                val daysEntity = Utils.addedItemPosition?.let { it1 ->
                    selectedStartTimeFormatted?.let { it2 ->
                        selectedEndTimeFormatted?.let { it3 ->
                            DaysEntity(
                                0,
                                it1,
                                it2,
                                it3
                            )
                        }
                    }
                }
                daysEntity?.let { it4 -> mViewModel?.insertDatatoDb(it4) }
            }

            startActivity(Intent(this, DetailsActivity::class.java))
            finish()
        })

    }

    fun showTimePickerDialog(isStart: Boolean) {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedCalendar.set(Calendar.MINUTE, minute)
                val selectedTimeInMillis = selectedCalendar.timeInMillis

                if (isStart) {
                    if (selectedEndTime != null && selectedTimeInMillis >= selectedEndTime!!) {
                        showToast("Start time must be smaller than end time")
                    } else {
                        selectedStartTime = selectedTimeInMillis
                        selectedStartTimeFormatted = getFormattedTime(selectedTimeInMillis)
                        mViewModel?.startTime?.value = getFormattedTime(selectedTimeInMillis)
                    }
                } else {
                    if (selectedStartTime != null && selectedTimeInMillis <= selectedStartTime!!) {
                        showToast("End time must be greater than start time")
                    } else {
                        if (selectedStartTime == null) {
                            showToast("Select the start time")
                        } else {
                            selectedEndTime = selectedTimeInMillis
                            selectedEndTimeFormatted = getFormattedTime(selectedTimeInMillis)
                            mViewModel?.endTime?.value = getFormattedTime(selectedTimeInMillis)
                        }

                    }
                }
            },
            hourOfDay,
            minute,
            false
        )

        timePickerDialog.show()

    }

    private fun getFormattedTime(timeInMillis: Long): String {
        val amPm = if (timeInMillis < 12 * 60 * 60 * 1000) "AM" else "PM"
        return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(timeInMillis))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupObservers() {
        mViewModel?.endTime?.observe(this, Observer {
            if (selectedStartTime != null && selectedEndTime != null) {
                if (!Utils.isFromAdd) {
                    when (Utils.LastPos) {
                        Constants.Sunday -> {
                            Log.e("Tag", "$selectedStartTimeFormatted , $selectedEndTimeFormatted")
                            mPreferences.putString(
                                Constants.Sunday.toString(),
                                "$selectedStartTimeFormatted,$selectedEndTimeFormatted"
                            )
                        }

                        Constants.Monday -> {
                            mPreferences.putString(
                                Constants.Monday.toString(),
                                "$selectedStartTimeFormatted,$selectedEndTimeFormatted"
                            )
                        }

                        Constants.Tuesday -> {
                            mPreferences.putString(
                                Constants.Tuesday.toString(),
                                "$selectedStartTimeFormatted,$selectedEndTimeFormatted"
                            )
                        }

                        Constants.Wednesday -> {
                            mPreferences.putString(
                                Constants.Wednesday.toString(),
                                "$selectedStartTimeFormatted,$selectedEndTimeFormatted"
                            )
                        }

                        Constants.Thursday -> {
                            mPreferences.putString(
                                Constants.Thursday.toString(),
                                "$selectedStartTimeFormatted,$selectedEndTimeFormatted"
                            )
                        }

                        Constants.Friday -> {
                            mPreferences.putString(
                                Constants.Friday.toString(),
                                "$selectedStartTimeFormatted,$selectedEndTimeFormatted"
                            )
                        }

                        Constants.Saturday -> {
                            mPreferences.putString(
                                Constants.Saturday.toString(),
                                "$selectedStartTimeFormatted,$selectedEndTimeFormatted"
                            )
                        }
                    }
                    mAdapter?.updateSelection(Utils.LastPos)
                    selectedStartTime = null
                    selectedEndTime = null
                    selectedStartTimeFormatted = null
                    selectedEndTimeFormatted = null
                }
            }
        })
    }

    private fun initViews() {
        binding?.mViewModel = mViewModel
        mPreferences = Preferences(this)
        createList()
        setupAdapter()
        if (Utils.isFromAdd) {
            binding?.rvDays?.visibility = View.GONE
        }
    }

    private fun createList() {
        mList.add(DaysModel(0, value = "S"))
        mList.add(DaysModel(1, value = "M"))
        mList.add(DaysModel(2, value = "T"))
        mList.add(DaysModel(3, value = "W"))
        mList.add(DaysModel(4, value = "T"))
        mList.add(DaysModel(5, value = "F"))
        mList.add(DaysModel(6, value = "S"))
    }

    private fun setupAdapter() {
        mAdapter = mViewModel?.let { DaysAdapter(this, mList, it, mPreferences) }
        binding?.rvDays?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.lifecycleOwner = this
    }
}