package com.example.codemaster.ui.screens.home

import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.ContestDetailsActivity
import com.example.codemaster.MyApplication
import com.example.codemaster.R
import com.example.codemaster.WebViewActivity
import com.example.codemaster.data.model.ContestItem
import com.example.codemaster.ui.screens.codeforces_problemset.Nul
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

val font = FontFamily.SansSerif

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
){
    Column {
        val state = viewModel.uiState2.collectAsState().value
        when(state){
            is HomeState.Loading -> {
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
            is HomeState.Failure -> {
                Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_LONG).show()
            }
            is HomeState.Success -> {
                ContestsDisplayScreen(data = state.data)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContestsDisplayScreen(
    data: List<ContestItem>
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
//            .padding(bottom = 50.dp)
    ) {
        items(data) {
            ContestCard(
                data = it
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContestCard(
    data: ContestItem,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Divider(
            modifier = Modifier.fillMaxSize(1f),
            color = Color(0xFFF3F3F3),
            thickness = 2.dp
        )
        Spacer(modifier = Modifier.height(0.dp))
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier.width(70.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ){
                if(data.site == "CodeChef") {
                    data.start_time.toDate()?.let {
                        val mon = it.formatTo("MMM")
                        val date = it.formatTo("dd")
                        val year = it.formatTo("yyyy")
                        Text(
                            text = mon,
                            fontFamily = font,
                            color = Color(0xFFFDD835),
                            fontSize = 11.sp
                        )
                        Text(
                            text = date,
                            fontFamily = font,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = year,
                            fontFamily = font,
                            fontSize = 11.sp
                        )
                    }
                }
                else {
                    val odt = OffsetDateTime.parse(data.start_time)
//                    val dtf = DateTimeFormatter.ofPattern("dd MMM, uuuu", Locale.ENGLISH)
                    val mon = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH)
                    val date = DateTimeFormatter.ofPattern("dd", Locale.ENGLISH)
                    val year = DateTimeFormatter.ofPattern("uuuu", Locale.ENGLISH)
                    Text(
                        text = mon.format(odt),
                        fontFamily = font,
                        color = Color(0xFFFDD835),
                        fontSize = 11.sp
                    )
                    Text(
                        text = date.format(odt),
                        fontFamily = font,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = year.format(odt),
                        fontFamily = font,
                        fontSize = 11.sp
                    )

                }
            }
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .width(250.dp)
                    .clickable {
                        val myIntent = Intent(MyApplication.instance, ContestDetailsActivity::class.java)
                        myIntent.putExtra("platform", data.site)
                        myIntent.putExtra("contest", data.name)
                        myIntent.putExtra("duration", data.duration)
                        myIntent.putExtra("startTime", data.start_time)
                        myIntent.putExtra("endTime", data.end_time)
                        myIntent.putExtra("url", data.url)
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        MyApplication.instance.startActivity(myIntent)
                    }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = data.icon),
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
                    fontWeight = Bold,
                    fontFamily = font
                )
//                no. of hours
//                val x = (data.duration).toIntOrNull()
//                val length = x?.div(3600)
//                if(length != null){
//                    Text(
//                        text = "Duration : $length hrs",
//                        fontFamily = font
//                    )
//                }
            }
        }
    }
}

fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date? {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}
fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}