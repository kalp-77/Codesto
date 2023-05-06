package com.example.codemaster.ui.screens.leetcode

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.R
import com.example.codemaster.components.PieChart
import com.example.codemaster.components.ProfileCard
import com.example.codemaster.components.graphs.DonutChart
import com.example.codemaster.components.graphs.ProgressGraph
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
            }
            is LeetcodeState.Success -> {
                Column(modifier = Modifier.padding(10.dp)) {
                    ProfileCard(
                        avatar = "",
                        handle = "",
                        rating = state.data.total_problems_solved,
                        maxRating = state.data.ranking,
                        platformIcon = R.drawable.leetcode,
                        rank = "Problem Count",
                        platform = "LEETCODE"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    ProgressGraph(
                        username = state.data.username,
                        easyQuestionsSolved = state.data.easy_questions_solved,
                        totalEasyQuestions = state.data.total_easy_questions,
                        mediumQuestionsSolved = state.data.medium_questions_solved,
                        totalMediumQuestions = state.data.total_medium_questions,
                        hardQuestionsSolved = state.data.hard_questions_solved,
                        totalHardQuestions = state.data.total_hard_questions
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(160.dp),
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                PieChart(
                                    progressDataList = listOf(
                                        state.data.easy_questions_solved.toFloat(),
                                        state.data.medium_questions_solved.toFloat(),
                                        state.data.hard_questions_solved.toFloat()
                                    ),
                                    size = 150.dp
                                )
                            }
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(160.dp),
                        ) {
                                // content of the second card
                        }
                    }
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth(0.5f)
//                                .height(200.dp)
//                        ) {
//                            Column(
//                                verticalArrangement = Arrangement.Center,
//                                horizontalAlignment = Alignment.CenterHorizontally
//                            ) {
//
//                            }
//                        }
//                        Spacer(modifier = Modifier.width(5.dp))
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth(0.5f)
//                                .height(200.dp)
//                        ) {
//
//                        }
//                    }
                }
            }
        }
    }
}



