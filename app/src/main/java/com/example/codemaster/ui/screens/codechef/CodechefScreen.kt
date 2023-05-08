package com.example.codemaster.ui.screens.codechef

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.MyApplication
import com.example.codemaster.R
import com.example.codemaster.WebViewActivity
import com.example.codemaster.components.BlockCard
import com.example.codemaster.components.CodechefProfileCard
import com.example.codemaster.components.HorizontalCarousel
import com.example.codemaster.components.graphs.LineChart
import com.example.codemaster.components.ProfileCard
import com.example.codemaster.components.graphs.LineChartCard
import com.example.codemaster.ui.theme.Black20
import com.example.codemaster.ui.theme.BlueG
import com.example.codemaster.ui.theme.Darkblack30
import com.example.codemaster.ui.theme.Divider10
import com.example.codemaster.ui.theme.Green10
import com.example.codemaster.ui.theme.GreenG


//val font = FontFamily(Font(R.font.varelaround_regular))
val font = FontFamily.SansSerif

@Composable
fun CodechefScreen(
    viewModel: CodechefViewModel = hiltViewModel(),
) {

    Column {
        val state = viewModel.uiState.collectAsState().value
        when(state){
            is CodechefState.Loading -> {
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
            is CodechefState.Failure ->{
                Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_LONG).show()
            }
            is CodechefState.Success -> {
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
                        CodechefProfileCard(
                            avatar = state.data.avatar,
                            handle = state.data.username,
                            rating = state.data.rating,
                            maxRating = state.data.max_rating,
                            platformIcon = R.drawable.codechef,
                            div = state.data.div,
                            platform = "CODECHEF",
                            stars = state.data.stars,
                            onClick = {
                                val myIntent =
                                    Intent(MyApplication.instance, WebViewActivity::class.java)
                                val url =
                                    "https://www.codechef.com/practice?end_rating=999&group=all&hints=0&itm_campaign=problems&itm_medium=home&limit=20&page=0&search=&sort_by=difficulty_rating&sort_order=asc&start_rating=0&tags=&topic=&video_editorial=0&wa_enabled=0"
                                myIntent.putExtra("key", url)
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                MyApplication.instance.startActivity(myIntent)
                            }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        LineChartCard(
                            linePlotLines = viewModel.graphDataState.value.linePlotLines,
                            size = viewModel.graphDataState.value.dataPoints.size,
                            maxRating = state.data.max_rating.toInt(),
                            ratingStatus = state.ratingStatus
                        )
                    }
                }
            }
        }
    }
}