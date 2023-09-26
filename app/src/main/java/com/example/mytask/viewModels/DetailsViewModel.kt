package com.example.mytask.viewModels

import androidx.lifecycle.ViewModel
import com.example.mytask.database.AppDao
import com.example.mytask.models.DaysEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(val appDao: AppDao) : ViewModel() {
    fun getDataByDayId(dayId: Int): MutableList<DaysEntity> {
        return appDao.getDataByDays(dayId)
    }
}