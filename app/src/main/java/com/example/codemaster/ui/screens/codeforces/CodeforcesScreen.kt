package com.example.codemaster.ui.screens.codeforces

import android.graphics.Typeface
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.R
import com.example.codemaster.components.HorizontalCarousel
import com.example.codemaster.components.ProfileCard
import com.example.codemaster.components.graphs.BarChart
import com.example.codemaster.components.graphs.LineChart
import com.example.codemaster.components.graphs.LineChartCard
import com.example.codemaster.data.model.Response
import com.example.codemaster.navigation.Screens
import com.example.codemaster.ui.theme.BarGraph10
import com.example.codemaster.ui.theme.Black20
import com.example.codemaster.ui.theme.BlueG
import com.example.codemaster.ui.theme.Darkblack30
import com.example.codemaster.ui.theme.Grid10
import com.example.codemaster.ui.theme.SnackbarGreen30
import com.example.codemaster.ui.theme.ThemeBlack30
import com.example.codemaster.ui.theme.ThemeLavender
import com.example.codemaster.utils.NavigateUI
import com.example.codemaster.utils.rememberChartStyle
import com.example.codemaster.utils.rememberMarker
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry


//val font = FontFamily(Font(R.font.varelaround_regular))
val font = FontFamily.SansSerif

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CodeforcesScreen(
    onNavigate: (route: Screens) -> Unit,
    viewModel: CodeforcesViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value
    val solvedProblemState = viewModel.solvedProblemState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect {
            when (it) {
                is NavigateUI.Navigate -> {
                    onNavigate(it.onNavigate)
                }
                is NavigateUI.Snackbar -> {

                }
                is NavigateUI.PopBackStack -> {

                }

                else -> {}
            }

        }
    }
    Column{
        //topBar()
        when (state) {
            is CodeforcesState.Loading -> {
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
            is CodeforcesState.Success ->{

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Darkblack30)
                        .verticalScroll(state = rememberScrollState())
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        ProfileCard(
                            avatar = state.data.userData.result[0].avatar,
                            handle = state.data.userData.result[0].handle,
                            rating = state.data.userData.result[0].rating.toString(),
                            maxRating = state.data.userData.result[0].maxRating.toString(),
                            platformIcon = R.drawable.codeforces,
                            rank = state.data.userData.result[0].rank.toUpperCase(),
                            platform = "CODEFORCES"
                        )
                        Spacer(modifier = Modifier.height(22.dp))
                        LineChartCard(
                            linePlotLines = viewModel.graphDataState.value.linePlotLines,
                            size = viewModel.graphDataState.value.dataPoints.size,
                            maxRating = state.data.userData.result[0].maxRating
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        HorizontalCarousel()
                        Spacer(modifier = Modifier.height(25.dp))
                        when (solvedProblemState.value) {
                            is Response.Loading -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator(
                                        color = Color(0xFFB3BCF8)
                                    )
                                }
                            }
                            is Response.Success -> {
                                BarChart(barGraphData = solvedProblemState.value?.data?.data, totalProblemSolved = viewModel.totalProblemSolved.value)
                            }
                            is Response.Failure -> {
                                Box(
                                    modifier = Modifier
                                        .height(100.dp)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.BottomEnd
                                ) {
                                    Text(text = "No Data found", color = Color.White)
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
            is CodeforcesState.Failure -> {
                Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }
}

