package com.example.waktuku.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("activity")
@Parcelize
data class ActivityModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val activity: String = "",
    val time: String = "",
    val audio: String = ""
): Parcelable