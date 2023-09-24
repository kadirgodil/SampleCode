package com.example.mytask.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytask.database.AppDao
import com.example.mytask.models.DaysEntity
import com.example.mytask.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel() : ViewModel() {

    val startTime = MutableLiveData<String>()
    val endTime = MutableLiveData<String>()

    val startTimeClicked = SingleLiveEvent<Void>()
    val endTimeClicked = SingleLiveEvent<Void>()

    @Inject
    lateinit var appDao: AppDao

    val continueButtonClicked = SingleLiveEvent<Void>()


    fun onStartTimeClicked() {
        startTimeClicked.call()
    }

    fun onEndTimeClicked() {
        endTimeClicked.call()
    }

    fun onContinueButtonClicked() {
        continueButtonClicked.call()
    }

    fun insertDatatoDb(entity: DaysEntity) {
        appDao.addData(entity)
    }


}