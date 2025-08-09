package com.revifaturahman.waktuku.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.revifaturahman.waktuku.data.dao.ActivityDao
import com.revifaturahman.waktuku.data.model.ActivityModel

@Database(
    entities =[ActivityModel::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun ActivityDao(): ActivityDao
}