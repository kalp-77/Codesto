package com.example.codemaster

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import com.example.codemaster.ContestDetailsActivity.Companion.TAG
import com.example.codemaster.alarm.Alarm
import com.example.codemaster.alarm.AlarmSchedulerImpl
import com.example.codemaster.alarm.AlarmSchedulerImpl.Companion.SHARED_PREFERENCES_NAME
import com.example.codemaster.ui.screens.home.formatTo
import com.example.codemaster.ui.screens.home.toDate
import com.example.codemaster.ui.theme.CodeMasterTheme
import com.example.codemaster.utils.DateTimeConverter
import com.google.gson.Gson

class ContestDetailsActivity : ComponentActivity() {
    companion object{
        val TAG = "TAG"
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeMasterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val platform = intent.getStringExtra("platform")!!
                    val contest = intent.getStringExtra("contest")!!
                    val duration = intent.getStringExtra("duration")!!
                    val startTime = intent.getStringExtra("startTime")!!
                    val endTime = intent.getStringExtra("endTime")!!
                    val url = intent.getStringExtra("url")!!
                    Greeting(platform, contest, duration, startTime, endTime, url)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting(
    platform: String,
    contest: String,
    duration: String,
    startTime: String,
    endTime: String,
    url: String
) {

    val scheduler = AlarmSchedulerImpl()
    val alarmId = "$contest.$startTime".hashCode()
    val context = LocalContext.current

    val alarm =  checkAlarm(context, alarmId)
    var isAlarmSet by remember { mutableStateOf(alarm?.isEnabled ?: false) }
    Log.d(TAG, "from sharedPref isAlarmSet: $isAlarmSet, isEnabled: ${alarm?.isEnabled}")


    val start_date: String
    val end_date: String
    val properStartTime: Long
    val properEndTime: Long

    //date
    if(platform == "CodeChef") {
        start_date = startTime.toDate()?.formatTo("dd MMM, yyyy - hh:mm a")!!
        end_date = endTime.toDate()?.formatTo("dd MMM, yyyy - HH:mm")!!
        properStartTime = DateTimeConverter().convertCCTimeToLong(startTime)
        properEndTime = DateTimeConverter().convertCCTimeToLong(endTime)

    }
    else {
        start_date = DateTimeConverter().convertStringToDate(startTime)
        end_date = DateTimeConverter().convertStringToDate(endTime)
        properStartTime = DateTimeConverter().convertTimeStringToLong(startTime)
        properEndTime = DateTimeConverter().convertTimeStringToLong(endTime)

    }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier,
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_work),
                contentDescription = "illustration"
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        Column (
            modifier = Modifier.padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = platform,
                modifier = Modifier,
                fontSize = 18.sp,
                color = Color(0xffB9B9B9)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = contest,
                modifier = Modifier.padding(10.dp),
                fontSize = 15.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        ConstraintLayout(
            modifier = Modifier.padding(0.dp),

            ){
            val (recordCircle, textStart, divider) = createRefs()
            val (circle, textEnd) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.icon__recordcircle),
                contentDescription = "null",
                modifier = Modifier
                    .constrainAs(recordCircle){
                        top.linkTo(parent.top)
                        bottom.linkTo(divider.top)
                        start.linkTo(circle.start)
                        end.linkTo(circle.end)
                    }
            )
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .background(Color(0xFFB6B6B6))
                    .height(60.dp)
                    .constrainAs(divider) {
                        bottom.linkTo(circle.top)
                        top.linkTo(recordCircle.bottom)
                        start.linkTo(circle.start)
                        end.linkTo(circle.end)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.icon_circle),
                contentDescription = "null",
                modifier = Modifier
                    .constrainAs(circle){
                        bottom.linkTo(parent.bottom)
                        top.linkTo(divider.bottom)
                    }
            )
            Row(
                modifier = Modifier
                    .constrainAs(textStart){
                        start.linkTo(recordCircle.end, margin = 10.dp)
                        top.linkTo(recordCircle.top)
                        bottom.linkTo(recordCircle.bottom)
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ){
                Text(text = start_date, modifier = Modifier, fontSize = 16.sp, color = Color.White)
                Spacer(modifier = Modifier.width(20.dp))
            }
            Row(
                modifier = Modifier
//                    .padding(20.dp)
                    .constrainAs(textEnd){
                        start.linkTo(recordCircle.end, margin = 10.dp)
                        top.linkTo(circle.top)
                        bottom.linkTo(circle.bottom)
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ){
                Text(text = end_date, modifier = Modifier,  fontSize = 16.sp,  color = Color.White)
                Spacer(modifier = Modifier.width(20.dp))
//                val x = (duration).toIntOrNull()
//                val length = x?.div(3600)
//                if(length != null) {
//                    Log.d("TAG", "length: $length")
//                    Log.d("TAG", "duration: $duration")
//
//                    Text(
//                        text = "$length hrs",
//                        modifier = Modifier,
//                        fontSize = 16.sp
//                    )
//                }
//                Text(text = endTime, modifier = Modifier,  fontSize = 16.sp)
            }

        }
        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (isAlarmSet) {
                            scheduler.cancel(context, alarmId)
                            isAlarmSet = false
                        } else {
                            scheduler.schedule(
                                context,
                                Alarm(
                                    alarmId, platform, contest, properStartTime, true
                                )
                            )
                            isAlarmSet = true
                        }
                        Log.d(TAG, "in Card: isAlarmSet: $isAlarmSet")
                        scheduler.updateAlarmEnabled(context, alarmId, isAlarmSet)
                    },
                colors = if (isAlarmSet) CardDefaults.cardColors(Color(0xff17422F)) else CardDefaults.cardColors(Color(0xff101016))
            ) {
                Box(
                    modifier = Modifier.padding(15.dp)
                ){
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.Alarm,
                            contentDescription ="alarm" ,
                            Modifier
                                .size(30.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (isAlarmSet) "Cancel Alarm" else "Set Alarm",
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        AddToCalendar(context, contest = contest, url = url, begin = properStartTime, end =properEndTime )
                    },
                colors = CardDefaults.cardColors(Color(0xff101016))

            ) {
                Box(
                    modifier = Modifier.padding(15.dp),
                ){
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "calendar",
                            Modifier.size(30.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "ADD TO CALENDAR",
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val myIntent = Intent(MyApplication.instance, WebViewActivity::class.java)
                        myIntent.putExtra("key", url)
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(myIntent)
                    },
                colors = CardDefaults.cardColors(Color(0xff101016))
            ) {
                Box(
                    modifier = Modifier.padding(15.dp),
                ){
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "website",
                            Modifier.size(30.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "VISIT WEBSITE",
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun checkAlarm(
    context: Context,
    alarmId: Int
): Alarm? {
    val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )
    val json = sharedPreferences.getString("ALARM_KEY", null)
    if (json != null) {
        val savedAlarms = Gson().fromJson(json, Array<Alarm>::class.java)
        for (alarm in savedAlarms) {
            if (alarm.id == alarmId) {
                Log.d(TAG, "checkAlarm: $alarm")
                return alarm
            }
        }
    }
    return null
}

fun AddToCalendar(
    context: Context,
    contest: String,
    url: String,
    begin: Long,
    end: Long
){


    val intent = Intent(Intent.ACTION_INSERT).apply {
        data = CalendarContract.Events.CONTENT_URI
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        putExtra(CalendarContract.Events.TITLE, contest)
        putExtra(CalendarContract.Events.DESCRIPTION, url)
        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
    }
    val packageManager = context.packageManager
    val resolveInfo = packageManager.resolveActivity(intent, 0)
    if (resolveInfo != null) {
        MyApplication.instance.startActivity(intent)
    }else{
        Toast.makeText(context, "There is no app that can support this action", Toast.LENGTH_SHORT).show()
    }
}