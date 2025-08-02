package com.example.waktuku.di

import android.content.Context
import androidx.room.Room
import com.example.waktuku.data.dao.ActivityDao
import com.example.waktuku.data.db.AppDatabase
import com.example.waktuku.repository.ActivityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "activity.db"
        ).build()
    }

    @Provides
    fun ProvideActivityDao(db: AppDatabase): ActivityDao = db.ActivityDao()

    fun provideRepository(
        activityDao: ActivityDao
    ): ActivityRepository {
        return ActivityRepository(activityDao)
    }
}