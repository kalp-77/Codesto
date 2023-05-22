package com.example.codemaster.alarm

import android.content.Context

interface AlarmScheduler {
    fun schedule(
        context: Context,
        alarm: Alarm
    )
    fun cancel(
        context: Context,
        alarmId : Int,
    )
}