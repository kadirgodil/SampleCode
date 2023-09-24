package com.example.mytask.models

data class DetailsModel(
    val position: Int,
    val value: String,
    var isSelected: Boolean = false
)