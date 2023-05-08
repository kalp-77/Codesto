package com.example.codemaster

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.codemaster.ui.screens.home.font
import com.example.codemaster.ui.screens.home.formatTo
import com.example.codemaster.ui.screens.home.toDate
import com.example.codemaster.ui.theme.CodeMasterTheme
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

class ContestDetailsActivity : ComponentActivity() {
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
                    val intent = intent
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

    val start_date: String
    val end_date: String
    var time: String = "a"
    //date
    if(platform == "CodeChef") {
        start_date = startTime.toDate()?.formatTo("dd MMM, yyyy - HH:mm")!!
        end_date = endTime.toDate()?.formatTo("dd MMM, yyyy - HH:mm")!!
    }
    else {
        val odtStart = OffsetDateTime.parse(startTime)
        val odtEnd = OffsetDateTime.parse(endTime)
        val dtf = DateTimeFormatter.ofPattern("dd MMM, uuuu - HH:mm", Locale.ENGLISH)
        start_date = dtf.format(odtStart)
        end_date = dtf.format(odtEnd)
    }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
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
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = contest, modifier = Modifier.padding(10.dp), fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))

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
                    .background(Color(0xFF000000))
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
                Text(text = start_date, modifier = Modifier,  fontSize = 16.sp)
                Spacer(modifier = Modifier.width(20.dp))
//                Text(text = time, modifier = Modifier,  fontSize = 16.sp)

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
                Text(text = end_date, modifier = Modifier,  fontSize = 16.sp)
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
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom
        ){
            Icon(
                imageVector = Icons.Default.Alarm,
                contentDescription ="alarm" ,
                Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(50.dp))
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "calendar", Modifier.size(30.dp))
            Spacer(modifier = Modifier.width(50.dp))
            IconButton(
                onClick = {
//                    Log.d("TAG", "url: $url")
//                    val myIntent = Intent(MyApplication.instance, WebViewActivity::class.java)
//                    myIntent.putExtra("key", url)
//                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "url",
                    Modifier.size(30.dp)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CodeMasterTheme {
//        Greeting("Android")
//    }
//}
