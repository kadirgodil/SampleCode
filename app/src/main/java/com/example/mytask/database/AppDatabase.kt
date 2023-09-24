package com.example.mytask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mytask.models.DaysEntity

@Database(entities = [DaysEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAppDao(): AppDao

    companion object {
        private var Instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            if (Instance == null) {
                Instance = Room.databaseBuilder(
                    context, AppDatabase::class.java, "App_DATABASE"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return Instance!!
        }

    }
}