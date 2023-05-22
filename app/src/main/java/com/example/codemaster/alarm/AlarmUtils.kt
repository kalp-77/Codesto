package com.example.codemaster.alarm

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.os.Vibrator

object AlarmUtils {
    lateinit var notificationManager: NotificationManager
    lateinit var alarmManager: AlarmManager
    lateinit var vibrator: Vibrator
    fun init(context: Context) {
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    }
}