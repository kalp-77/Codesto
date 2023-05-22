package com.example.codemaster.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.codemaster.ContestDetailsActivity.Companion.TAG
import com.example.codemaster.alarm.AlarmUtils.alarmManager
import com.example.codemaster.alarm.receivers.AlarmReceiver
//import com.example.codemaster.alarm.receivers.AlarmReceiver
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.log

class AlarmSchedulerImpl : AlarmScheduler {
    companion object{
        const val SHARED_PREFERENCES_NAME = "alarm_prefs"
    }

    @SuppressLint("ScheduleExactAlarm")
    override fun schedule(
        context: Context,
        alarm: Alarm
    ) {
        val triggerTime = alarm.time - (30 * 60 * 1000)
        val startTimeString = SimpleDateFormat("HH:mm a", Locale.getDefault()).format(Date(alarm.time))
        Log.d(TAG, "startTime: $startTimeString")

        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        alarmIntent.putExtra("alarmObj", alarm)
        alarmIntent.putExtra("startTimeString", startTimeString)
        Log.d(TAG, "triggerTime: $triggerTime")
        Log.d(TAG, "startTimeString: $startTimeString")

        val alarmPI: PendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.id,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, alarmPI)
        Toast.makeText(context, "alarm set", Toast.LENGTH_SHORT).show()

        addAlarmToSharedPreferences(context, alarm)
    }

    override fun cancel(
        context: Context,
        alarmId: Int
    ) {
        removeAlarmFromSharedPreferences(context, alarmId)

        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val alarmPI: PendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(alarmPI)

        Toast.makeText(context, "alarm cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun addAlarmToSharedPreferences(
        context: Context,
        alarm: Alarm
    ) {
        val sharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        val json = sharedPreferences.getString("ALARM_KEY", null)
        val savedAlarms: MutableList<Alarm> = mutableListOf()
        if (json != null) {
            savedAlarms.addAll(
                Gson().fromJson(json, Array<Alarm>::class.java).toMutableList()
            )
        }
        // Check if an alarm with the same ID already exists
        val isAlarmExists = savedAlarms.any { it.id == alarm.id }
        if (isAlarmExists) {
            return
        }else{
            savedAlarms.add(alarm)
        }
        // Save the updated list back to shared preferences
        val updatedJson = Gson().toJson(savedAlarms)
        sharedPreferences.edit().putString("ALARM_KEY", updatedJson).apply()
    }

    private fun removeAlarmFromSharedPreferences(context: Context, alarmId: Int) {
        val sharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        val json = sharedPreferences.getString("ALARM_KEY", null)
        if (json != null) {
            val savedAlarms = Gson().fromJson(json, Array<Alarm>::class.java)
            val updatedAlarms = savedAlarms.filter { it.id != alarmId }.toTypedArray()
            val updatedJson = Gson().toJson(updatedAlarms)
            sharedPreferences.edit().putString("ALARM_KEY", updatedJson).apply()
        }
    }

    fun updateAlarmEnabled(context: Context, alarmId: Int, isEnabled: Boolean) {
        val sharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        val json = sharedPreferences.getString("ALARM_KEY", null)
        if (json != null) {
            val savedAlarms = Gson().fromJson(json, Array<Alarm>::class.java)
            for (alarm in savedAlarms) {
                if (alarm.id == alarmId) {
                    alarm.isEnabled = isEnabled
                    val updatedJson = Gson().toJson(savedAlarms)
                    sharedPreferences.edit().putString("ALARM_KEY", updatedJson).apply()
                    break
                }
            }
        }
    }

}