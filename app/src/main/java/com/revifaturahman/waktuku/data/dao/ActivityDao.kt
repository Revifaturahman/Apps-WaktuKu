package com.revifaturahman.waktuku.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.revifaturahman.waktuku.data.model.ActivityModel

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: ActivityModel): Long

    @Update
    suspend fun update(activity: ActivityModel)

    @Delete
    suspend fun delete(activity: ActivityModel)

    @Query("SELECT * FROM activity")
    suspend fun getAll(): List<ActivityModel>
}