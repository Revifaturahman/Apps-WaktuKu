package com.example.waktuku.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import com.example.waktuku.utils.AlarmReceiver
import java.util.*

object AlarmHelper {
    fun setAlarm(
        context: Context,
        id: Int,
        activityName: String,
        hour: Int,
        minute: Int,
        audioPath: String?
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // penting
                context.startActivity(intent)
                return
            }
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("activity_name", activityName) // ini baru ditambah
            putExtra("text", "Waktunya $activityName!") // ini untuk TTS dan notifikasi
            putExtra("audio_path", audioPath) // bisa null, tidak masalah
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}
