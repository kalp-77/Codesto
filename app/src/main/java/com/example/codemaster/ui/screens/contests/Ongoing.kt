package com.example.codemaster.ui.screens.contests

import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.MyApplication
import com.example.codemaster.R
import com.example.codemaster.WebViewActivity
import com.example.codemaster.data.model.Contest
import com.example.codemaster.data.model.ContestItem
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OngoingContestsScreen(
    contestViewModel: ContestViewModel = hiltViewModel()
){
    Column {
        val state = contestViewModel.uiState.collectAsState().value
        when(state){
            is ContestState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFFB3BCF8)
                    )
                }
            }
            is ContestState.Failure -> {
                Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_LONG).show()
            }
            is ContestState.Success -> {
                OngoingContestsDisplayScreen(data = state.data)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OngoingContestsDisplayScreen(
    data : Contest,
){
    val list = data.filter {  it.status == "CODING" }
    if(list.isEmpty()){
//        Nul("No Upcoming Contests!")
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 52.dp)
    ) {
        items(list){
            OngoingCard(data = it)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OngoingCard(
    data: ContestItem,
//    intent: Intent
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Divider(
            modifier = Modifier.fillMaxSize(1f),
            color = Color(0xFFF3F3F3),
            thickness = 2.dp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .width(250.dp)
                    .clickable {
                        val myIntent = Intent(MyApplication.instance, WebViewActivity::class.java)
                        myIntent.putExtra("key", data.url)
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        MyApplication.instance.startActivity(myIntent)
                    }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val painter: Painter
                    if(data.site == "CodeChef")
                        painter = painterResource(id = R.drawable.icons_codechef)
                    else if(data.site == "CodeForces")
                        painter = painterResource(id = R.drawable.icons_codeforces)
                    else if(data.site == "LeetCode")
                        painter = painterResource(id = R.drawable.icons_leetcode)
                    else if(data.site == "HackerRank")
                        painter = painterResource(id = R.drawable.icons_hackerrank)
                    else if(data.site == "HackerEarth")
                        painter = painterResource(id = R.drawable.icons_hackerearth)
                    else if(data.site == "AtCoder")
                        painter = painterResource(id = R.drawable.icons_atcoder)
                    else
                        painter = painterResource(id = R.drawable.icons_google)
                    Image(
                        painter = painter,
                        contentDescription = "logo",
                    )
                    Text(
                        text = data.site,
                        modifier = Modifier.padding(8.dp),
                        fontFamily = font
                    )
                }
                Text(
                    text = data.name,
                    fontWeight = FontWeight.Bold,
                    fontFamily = font
                )

                //date
                if(data.site == "CodeChef") {
                    Text(
                        text =  "Start : ${data.start_time.toDate()?.formatTo("dd MMM, yyyy")}",
                        fontFamily = font
                    )
                }
                else {
                    val odt = OffsetDateTime.parse(data.start_time)
                    val dtf = DateTimeFormatter.ofPattern("dd MMM, uuuu", Locale.ENGLISH)
                    Text(
                        text = "Start : ${ dtf.format(odt) }",
                        fontFamily = font
                    )
                }

                //no. of hours
                val x = (data.duration).toIntOrNull()
                val length = x?.div(3600)
                if(length != null){
                    Text(
                        text = "Duration : ${length.toString()} hrs",
                        fontFamily = font
                    )
                }
            }
            Column(
                modifier = Modifier.width(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.icons_alarm),
                    contentDescription = "Reminder",
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterHorizontally)
//                        .clickable {
//                            intent.putExtra("platform", data.site)
//                            intent.putExtra("contest", data.name)
//                            alarmService.setAlarm(
//                                MyApplication.instance,
//                                data.site,
//                                data.name,
//                                data.start_time
//                            )
//                        },
                )
            }
        }
    }
}

