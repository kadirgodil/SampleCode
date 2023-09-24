package com.example.mytask.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Mytask")
@Parcelize
data class DaysEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "dayId")
    val dayId: Int,
    @ColumnInfo(name = "starttime")
    val startTime: String,
    @ColumnInfo(name = "endtime")
    val endTime: String
) : Parcelable {
}