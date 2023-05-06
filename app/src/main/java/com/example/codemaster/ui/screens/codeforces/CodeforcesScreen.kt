package com.example.codemaster.ui.screens.codeforces

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.R
import com.example.codemaster.components.BlockCard
import com.example.codemaster.components.CustomSnackbar
import com.example.codemaster.components.HorizontalCarousel
import com.example.codemaster.components.graphs.LineChart
import com.example.codemaster.components.ProfileCard
import com.example.codemaster.navigation.Screens
import com.example.codemaster.ui.theme.Black20
import com.example.codemaster.ui.theme.BlueG
import com.example.codemaster.ui.theme.Chart
import com.example.codemaster.ui.theme.RedG
import com.example.codemaster.utils.NavigateUI


//val font = FontFamily(Font(R.font.varelaround_regular))
val font = FontFamily.SansSerif

@Composable
fun CodeforcesScreen(
    onNavigate: (route: Screens) -> Unit,
    viewModel: CodeforcesViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Black20)
                        .padding(15.dp)
                ) {
                    ProfileCard(
                        avatar = state.data.userData.result[0].avatar,
                        handle = state.data.userData.result[0].handle,
                        rating = state.data.userData.result[0].rating.toString(),
                        maxRating = state.data.userData.result[0].maxRating.toString(),
                        platformIcon = R.drawable.cficon,
                        rank = state.data.userData.result[0].rank.toUpperCase(),
                        platform = "CODEFORCES"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalCarousel()
                    Spacer(modifier = Modifier.height(15.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Black20)
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 20.dp, start = 20.dp, bottom = 4.dp),
                            fontSize = 13.sp,
                            text = "Rating Change",
                            color = Color.LightGray
                        )
                        LineChart(
                            linePlotLines = viewModel.graphDataState.value.linePlotLines,
                            size = state.data.graphData.contest.size
                        )
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



