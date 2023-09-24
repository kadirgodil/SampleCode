package com.example.mytask.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DaysModel(
    val postion: Int,
    var isSelected: Boolean = false,
    val value: String
) : Parcelable