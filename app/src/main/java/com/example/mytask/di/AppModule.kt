package com.example.mytask.di

import android.content.Context
import com.example.mytask.database.AppDao
import com.example.mytask.database.AppDatabase
import com.example.mytask.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideAppDao(appDatabase: AppDatabase): AppDao {
        return appDatabase.getAppDao()
    }

    @Provides
    @Singleton
    fun providePreference(@ApplicationContext context: Context): Preferences {
        return Preferences(context)
    }
}