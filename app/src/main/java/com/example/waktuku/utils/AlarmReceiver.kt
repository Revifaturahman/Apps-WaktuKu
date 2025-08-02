package com.example.waktuku.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.waktuku.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val activityName = intent.getStringExtra("activity_name") ?: "aktivitas"
        val message = intent.getStringExtra("text") ?: "Waktunya $activityName!"
        val audioPath = intent.getStringExtra("audio_path")
        val notificationId = System.currentTimeMillis().toInt()

        // 🔊 Mainkan audio jika tersedia
        if (!audioPath.isNullOrEmpty()) {
            try {
                Log.d("AlarmReceiver", "Mencoba memutar audio di path: $audioPath")
                val mediaPlayer = MediaPlayer().apply {
                    setDataSource(audioPath)
                    setAudioStreamType(AudioManager.STREAM_ALARM)
                    prepare()
                    start()
                }
            } catch (e: Exception) {
                Log.e("AlarmReceiver", "Gagal memutar audio: ${e.message}")
            }
        } else {
            Log.w("AlarmReceiver", "Tidak ada audio path yang diberikan.")
        }

        // 🔔 Tampilkan notifikasi
        val notification = NotificationCompat.Builder(context, "activity_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Pengingat: $activityName")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        if (Build.VERSION.SDK_INT < 33 || ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context).notify(notificationId, notification)
        } else {
            Log.w("AlarmReceiver", "Izin notifikasi belum diberikan.")
        }

        // 📋 Logging debug
        Log.d("AlarmReceiver", "Alarm aktif! activity=$activityName, message=$message, audio=$audioPath")
    }
}
