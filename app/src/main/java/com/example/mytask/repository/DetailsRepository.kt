package com.example.mytask.repository

import com.example.mytask.database.AppDao
import com.example.mytask.models.DaysEntity
import javax.inject.Inject

class DetailsRepository @Inject constructor(val appDao: AppDao) {

    fun removeDayFromDb(timeId: Int) {
        appDao.deleteDay(timeId)
    }

    fun addDaytoDb(daysEntity: DaysEntity) {
        appDao.addData(daysEntity)
    }

    fun getSelectedDays(dayid: Int): MutableList<DaysEntity> {
        return appDao.getDataByDays(dayid)
    }

}