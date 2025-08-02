package com.example.waktuku.repository

import com.example.waktuku.data.dao.ActivityDao
import com.example.waktuku.data.model.ActivityModel
import javax.inject.Inject

class ActivityRepository @Inject constructor(
    private val ActivityDao: ActivityDao
) {
    suspend fun insertActivity(activity: ActivityModel) = ActivityDao.insert(activity)
    suspend fun insertActivityAndReturnId(activity: ActivityModel): Long {
        return ActivityDao.insert(activity)
    }

    suspend fun updateActivity(activity: ActivityModel) = ActivityDao.update(activity)
    suspend fun deleteActivity(activity: ActivityModel) = ActivityDao.delete(activity)
    suspend fun getAllActivity(): List<ActivityModel> = ActivityDao.getAll()
}