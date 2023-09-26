package com.example.mytask.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mytask.models.DaysEntity

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addData(daysEntity: DaysEntity)

    @Query("select * from Mytask")
    fun getData(): MutableList<DaysEntity>

    @Query("select * from Mytask where dayId=:id")
    fun getDataByDays(id: Int): MutableList<DaysEntity>

    @Query("select exists (select * from Mytask where dayId=:id)")
    fun isExists(id: Int): Boolean

    @Query("Delete from Mytask where id=:id")
    fun deleteDay(id: Int)

    @Query("Delete from Mytask")
    fun deleteAll()
}