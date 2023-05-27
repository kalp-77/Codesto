package com.example.codemaster

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codemaster.alarm.AlarmSchedulerImpl
import com.example.codemaster.alarm.AlarmUtils.notificationManager
import com.example.codemaster.alarm.AlarmUtils.vibrator
import com.example.codemaster.alarm.receivers.AlarmReceiver.Companion.mediaPlayer
//import com.example.codemaster.alarm.receivers.AlarmReceiver.Companion.mediaPlayer
import com.example.codemaster.ui.theme.CodeMasterTheme
import com.example.codemaster.ui.theme.Purple40

class AlarmDetailsActivity : ComponentActivity() {

    private val timer = object : CountDownTimer(120000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
        }

        override fun onFinish() {
            mediaPlayer.stop()
            mediaPlayer.release()
            vibrator.cancel()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        timer.start()

        val scheduler = AlarmSchedulerImpl()
        val alarmId = intent?.getIntExtra("alarmId",0)
        val platformName = intent?.getStringExtra("platform")
        val contestName = intent?.getStringExtra("contest")
        val startTimeString = intent?.getStringExtra("startTimeString")
        Log.d("TAG", "alarmActivity: $alarmId, $contestName, $startTimeString ")

        setContent {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1){
                setShowWhenLocked(true)
                setTurnScreenOn(true)
                val keyguardManager = applicationContext.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
                keyguardManager.requestDismissKeyguard(this, null)

            }else{
                window.addFlags(
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                )
            }
            CodeMasterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    Column(
                        modifier = Modifier.wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = startTimeString.toString(),
                            fontSize = 25.sp,
                            color = Color.White
                            )
                        Text(
                            text = platformName.toString(),
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Text(
                            text = contestName.toString(),
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Button(
                            onClick = {
                                Log.d("TAG", "onClick: $alarmId")
                                scheduler.cancel(this@AlarmDetailsActivity, alarmId!!)
                                notificationManager.cancel(alarmId)
                                finish()
                            },
                            modifier = Modifier,
                            colors = ButtonDefaults.buttonColors(Purple40)
                        ) {
                            Box(
                                modifier = Modifier.padding(10.dp),
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = "DISMISS",
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
        vibrator.cancel()
        Log.d("TAG", "Activity destroyed")
    }
}
