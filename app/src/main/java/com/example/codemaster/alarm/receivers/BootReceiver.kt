package com.example.codemaster.alarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.codemaster.alarm.Alarm
import com.example.codemaster.alarm.AlarmSchedulerImpl
import com.example.codemaster.alarm.AlarmSchedulerImpl.Companion.SHARED_PREFERENCES_NAME
import com.google.gson.Gson

class BootReceiver : BroadcastReceiver() {
    private val scheduler = AlarmSchedulerImpl()

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TAG", "onReceive: BootReceiver Started")
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED){
            val sharedPreferences = context?.getSharedPreferences(
                SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
            val json = sharedPreferences?.getString("ALARM_KEY", null)
            val savedAlarms: List<Alarm> = if(json != null){
                Gson().fromJson(json, Array<Alarm>::class.java).toList()
            } else{
                emptyList()
            }

            for(alarm in savedAlarms){
                if(alarm.time < System.currentTimeMillis()){
                    scheduler.cancel(context!!, alarm.id)
                }else{
                    scheduler.schedule(context!!, alarm)
                }
            }
        }
    }

}

