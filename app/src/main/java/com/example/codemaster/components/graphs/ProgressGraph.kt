package com.example.codemaster.components.graphs

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codemaster.components.PieChart
import com.example.codemaster.ui.screens.leetcode.font

@Composable
fun ProgressGraph(
    username : String,
    easyQuestionsSolved : String,
    totalEasyQuestions : String,
    mediumQuestionsSolved : String,
    totalMediumQuestions : String,
    hardQuestionsSolved : String,
    totalHardQuestions : String
){
    Column{
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(
                        text = "@${username}",
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = font,
                        color = Color(0xFF2A265C)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()){
                    Column(modifier = Modifier) {
                        Box {
                            Row {
                                Text(
                                    text = "Easy",
                                    color = Color(0xFF00B7A2),
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = font
                                )
                                Text(
                                    text = buildAnnotatedString {
                                        append(easyQuestionsSolved)
                                        pushStyle(SpanStyle(Color.Gray, fontSize = 13.sp))
                                        append("/${totalEasyQuestions}")
                                        pop()
                                    },
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.padding(start = 15.dp),
                                    fontFamily = font
                                )
                            }
                        }
                        Box {
                            LinearProgressIndicatorSample(
                                que = easyQuestionsSolved.toFloat(),
                                total_que = totalEasyQuestions.toFloat(),
                                color = Color(0xFF00B7A2),
                                trackColor = Color(0xFFDDEEE1)
                            )
                        }
                        Box(modifier = Modifier.padding(top = 10.dp)) {
                            Row {
                                Text(
                                    text = "Medium",
                                    color = Color(0xFFFEBF1E),
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = font
                                )
                                Text(
                                    text = buildAnnotatedString {
                                        append(mediumQuestionsSolved)
                                        pushStyle(SpanStyle(Color.Gray, fontSize = 13.sp))
                                        append("/${totalMediumQuestions}")
                                        pop()
                                    },
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.padding(start = 15.dp),
                                    fontFamily = font
                                )
                            }
                        }
                        Box {
                            LinearProgressIndicatorSample(
                                que = mediumQuestionsSolved.toFloat(),
                                total_que = totalMediumQuestions.toFloat(),
                                color = Color(0xFFFEBF1E),
                                trackColor = Color(0xFFF1EDE1)
                            )
                        }
                        Box(modifier = Modifier.padding(top = 10.dp)) {
                            Row {
                                Text(
                                    text = "Hard", color = Color(0xFFEE4743),
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = font,
                                )
                                Text(
                                    text = buildAnnotatedString {
                                        append(hardQuestionsSolved)
                                        pushStyle(SpanStyle(Color.Gray, fontSize = 13.sp))
                                        append("/${totalHardQuestions}")
                                        pop()
                                    },
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.padding(start = 15.dp),
                                    fontFamily = font
                                )
                            }
                        }
                        Box {
                            LinearProgressIndicatorSample(
                                que = hardQuestionsSolved.toFloat(),
                                total_que = totalHardQuestions.toFloat(),
                                color = Color(0xFFEE4743),
                                trackColor = Color(0xFFF5E0E1)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(6.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            PieChart(
                                progressDataList = listOf(
                                    easyQuestionsSolved.toFloat(),
                                    mediumQuestionsSolved.toFloat(),
                                    hardQuestionsSolved.toFloat()
                                ),
                                size = 130.dp
                            )
                        }
                    }
                }
            }
        }
    }
}
