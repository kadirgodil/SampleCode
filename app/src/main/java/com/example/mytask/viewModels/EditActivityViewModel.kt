package com.example.mytask.viewModels

import androidx.lifecycle.ViewModel
import com.example.mytask.models.DaysEntity
import com.example.mytask.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditActivityViewModel @Inject constructor(val detailsRepository: DetailsRepository) :
    ViewModel() {

    fun removeDayFromDb(dayId: Int) {
        detailsRepository.removeDayFromDb(dayId)
    }

    fun addDayToDb(daysEntity: DaysEntity) {
        detailsRepository.addDaytoDb(daysEntity)
    }

    fun getDatabyDays(dayid: Int): MutableList<DaysEntity> {
        return detailsRepository.getSelectedDays(dayid)
    }


}