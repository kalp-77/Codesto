package com.example.codemaster.alarm.receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat
import com.example.codemaster.AlarmDetailsActivity
import com.example.codemaster.ContestDetailsActivity.Companion.TAG
import com.example.codemaster.R
import com.example.codemaster.alarm.Alarm
import com.example.codemaster.alarm.AlarmUtils.notificationManager
import com.example.codemaster.alarm.AlarmUtils.vibrator

class AlarmReceiver: BroadcastReceiver() {

    companion object {
        lateinit var mediaPlayer: MediaPlayer
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {

        val defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        mediaPlayer = MediaPlayer.create(context, defaultRingtoneUri)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
        vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 500), 0))

        val alarmObj = intent?.getParcelableExtra<Alarm>("alarmObj")!!
        val startTimeString = intent.getStringExtra("startTimeString")

        val notifIntent = Intent(context?.applicationContext, AlarmDetailsActivity::class.java)
        notifIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        notifIntent.putExtra("alarmId", alarmObj.id)
        notifIntent.putExtra("platform", alarmObj.name)
        notifIntent.putExtra("contest", alarmObj.description)
        notifIntent.putExtra("startTimeString", startTimeString)
        val notifPI = PendingIntent.getActivity(context,alarmObj.id,notifIntent, PendingIntent.FLAG_IMMUTABLE)


        val builder = NotificationCompat.Builder(context!!, "my_channel_id")
            .setContentTitle(alarmObj.name)
//            .setContentText(contestName)
            .setContentText("Contest starts in 30 minutes")
            .setSubText("Click to view details")
            .setSmallIcon(R.drawable.icons_alarm)
            .setColor(ResourcesCompat.getColor(context.resources, R.color.purple_200, null))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAllowSystemGeneratedContextualActions(false)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setFullScreenIntent(notifPI, true)
            .setOngoing(true)
        notificationManager.notify(alarmObj.id, builder.build())

    }
}