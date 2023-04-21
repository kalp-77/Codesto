package com.example.codemaster.ui.screens.leetcode

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.MyApplication
import com.example.codemaster.WebViewActivity
import com.example.codemaster.components.DonutChart
import com.example.codemaster.components.LinearProgressIndicatorSample
import com.example.codemaster.data.model.Leetcode

val font = FontFamily.SansSerif

@Composable
fun LeetcodeScreen(
    leetcodeViewModel: LeetcodeViewModel = hiltViewModel()
){
    val heightInPx = with(LocalDensity.current) { LocalConfiguration.current
        .screenHeightDp.dp.toPx()
    }
    Column {
        //topBar()
        val state = leetcodeViewModel.uiState.collectAsState().value
        when(state){
            is LeetcodeState.Loading -> {
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
            is LeetcodeState.Failure ->{
                Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_LONG).show()
//                ErrorDialog(state.message)
            }
            is LeetcodeState.Success -> {
                LeetcodeDisplayScreen(state.data)
            }
            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeetcodeDisplayScreen(
    data : Leetcode
){
    Box(
        modifier = Modifier
            .background(Color(0xFFEEF0FD))
            .fillMaxSize()
    ){
        Column(modifier = Modifier.padding(10.dp)) {
            Row(modifier = Modifier.padding(10.dp) ){
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(5.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp) ) {
                        Row(modifier = Modifier.padding(bottom = 10.dp)){
                            Column {
                                Text(
                                    text = "@${data.username}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = font,
                                    color = Color(0xFF2A265C),
                                )
                            }
                        }
                        Column(modifier = Modifier.padding(bottom = 10.dp)){
                            Box{
                                Row{
                                    Text(
                                        text = "Easy",
                                        color = Color(0xFF00B7A2),
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = font
                                    )
                                    Text(
                                        text = buildAnnotatedString {
                                            append(data.easy_questions_solved)
                                            pushStyle(SpanStyle(Color.Gray, fontSize = 13.sp))
                                            append("/${data.total_easy_questions}")
                                            pop()
                                        },
                                        textAlign = TextAlign.End,
                                        modifier = Modifier.padding(start = 15.dp),
                                        fontFamily = font
                                    )
                                }
                            }
                            Box{
                                LinearProgressIndicatorSample(
                                    que = data.easy_questions_solved.toFloat(),
                                    total_que = data.total_easy_questions.toFloat(),
                                    color = Color(0xFF00B7A2),
                                    trackColor = Color(0xFFDDEEE1)
                                )
                            }
                            Box(modifier = Modifier.padding(top = 10.dp)) {
                                Row{
                                    Text(
                                        text = "Medium",
                                        color = Color(0xFFFEBF1E),
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = font
                                    )
                                    Text(
                                        text = buildAnnotatedString {
                                            append(data.medium_questions_solved)
                                            pushStyle(SpanStyle(Color.Gray, fontSize = 13.sp))
                                            append("/${data.total_medium_questions}")
                                            pop()
                                        },
                                        textAlign = TextAlign.End,
                                        modifier = Modifier.padding(start = 15.dp),
                                        fontFamily = font
                                    )
                                }
                            }
                            Box{
                                LinearProgressIndicatorSample(
                                    que = data.medium_questions_solved.toFloat(),
                                    total_que = data.total_medium_questions.toFloat(),
                                    color = Color(0xFFFEBF1E),
                                    trackColor = Color(0xFFF1EDE1)
                                )
                            }
                            Box(modifier = Modifier.padding(top = 10.dp)){
                                Row{
                                    Text(
                                        text = "Hard",color = Color(0xFFEE4743),
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = font,
                                    )
                                    Text(
                                        text = buildAnnotatedString {
                                            append(data.hard_questions_solved)
                                            pushStyle(SpanStyle(Color.Gray, fontSize = 13.sp))
                                            append("/${data.total_hard_questions}")
                                            pop()
                                        },
                                        textAlign = TextAlign.End,
                                        modifier = Modifier.padding(start = 15.dp),
                                        fontFamily = font
                                    )
                                }
                            }
                            Box{
                                LinearProgressIndicatorSample(
                                    que = data.hard_questions_solved.toFloat(),
                                    total_que = data.total_hard_questions.toFloat(),
                                    color = Color(0xFFEE4743),
                                    trackColor = Color(0xFFF5E0E1)
                                )
                            }
                        }
                        Text(
                            text = "Total Solved: ${data.total_problems_solved}",
                            fontFamily = font,
                            color = Color(0xFF2A265C),
                        )
                    }
                }
            }
            Row(modifier = Modifier.padding(10.dp)){
                val url = "https://leetcode.com/problemset/all/"
                Card(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth(),
                    onClick = {
                        val myIntent = Intent(MyApplication.instance, WebViewActivity::class.java)
                        myIntent.putExtra("key", url)
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        MyApplication.instance.startActivity(myIntent)
                    },
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(5.dp)
                ) {
                    Text(
                        text = "PROBLEMS",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentSize(),
                        fontFamily = font,
                        color = Color(0xFF2A265C)
                    )
                }
            }
            Row(modifier = Modifier.padding(10.dp)) {
                DonutChart(
                    modifier = Modifier,
                    progress = listOf(
                        data.easy_questions_solved.toFloat(),
                        data.medium_questions_solved.toFloat(),
                        data.hard_questions_solved.toFloat()
                    ),
                    colors = listOf(
                        Color(0xFF00B7A2),
                        Color(0xFFFEBF1E),
                        Color(0xFFEE4743),
                    ),
                    isDonut = true,
                    percentColor = Color.Black,
                )
            }
        }
    }
}