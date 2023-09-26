package com.example.mytask.litsners

import com.example.mytask.models.DaysEntity

interface onDayPosition {
    fun onDaySelected(position: Int): MutableList<DaysEntity>
}