package com.example.codemaster

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.codemaster.alarm.AlarmUtils
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        AlarmUtils.init(this)
        createNotificationChannel()
    }

    companion object {
        lateinit var instance: MyApplication
            private set
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Notification Channel"
            val descriptionText = "Description of My Notification Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("my_channel_id", name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}