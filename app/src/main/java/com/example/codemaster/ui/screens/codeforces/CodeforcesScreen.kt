package com.example.codemaster.ui.screens.codeforces

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.codemaster.data.model.Codeforces
import com.example.codemaster.navigation.Screens
import com.example.codemaster.utils.NavigateUI
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot


//val font = FontFamily(Font(R.font.varelaround_regular))
val font = FontFamily.SansSerif

@Composable
fun CodeforcesScreen(
//    topBar : @Composable ()->Unit,
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
                CodeforcesDisplayScreen(state.data)
            }
            is CodeforcesState.Failure -> {
//                ErrorDialog(state.message)
                Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeforcesDisplayScreen (
    data : CodeforcesScreenData,
    viewModel: CodeforcesViewModel = hiltViewModel()
) {
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
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(3.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp) ) {
                        Row {
                            Column(modifier = Modifier.padding(10.dp)) {
                                val painter = rememberAsyncImagePainter(model = data.userData.result[0].avatar)
                                Image(
                                    painter = painter,
                                    contentDescription = "Profile_picture",
                                    modifier = Modifier
                                        .size(80.dp)
                                )
                            }
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "@${data.userData.result[0].handle}",
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF2A265C),
                                    fontFamily = font,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = data.userData.result[0].rank,
                                    fontFamily = font,
                                    color = Color(0xFF2A265C),
                                )
                                Text(
                                    text = "Max Rating: ${data.userData.result[0].rating}",
                                    fontFamily = font,
                                    color = Color(0xFF2A265C),
                                )
                                Text(
                                    text = "Rating: ${data.userData.result[0].maxRating}",
                                    fontFamily = FontFamily.SansSerif, // font
                                    color = Color(0xFF2A265C),
                                )
                            }
                        }
                    }
                }
            }
            Row(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Card(
                    modifier = Modifier
                        .height(80.dp)
                        .width(140.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(3.dp),
                    onClick = {
                        viewModel.onEvent(NavigateUI.Navigate(Screens.ProblemsetScreen))
                    },
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Box(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ){
                        Text(
                            text = "PROBLEMS",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.wrapContentSize(),
                            fontFamily = FontFamily.SansSerif, // font
                            color = Color(0xFF2A265C),
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .height(80.dp)
                        .width(140.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    onClick = {
                        viewModel.onEvent(NavigateUI.Navigate(Screens.RatingChangeScreen))
                    },
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {
                    Text(
                        text = "RATINGS",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentSize(),
                        fontFamily = FontFamily.SansSerif, // font
                        color = Color(0xFF2A265C),
                    )
                }
            }
            Row(modifier = Modifier.padding(10.dp)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {
                    CodeforcesGraph(graphData = data.graphData)
                }
            }
        }
    }
}

@Composable
fun CodeforcesGraph(
    graphData: Codeforces
){
    val mylist = mutableListOf<DataPoint>()
    var i = 0
    for(j in graphData.contest.reversed()) {
        mylist.add(DataPoint(i.toFloat(), j.toFloat()))
        i += 1
    }
    val lines: List<List<DataPoint>> = listOf(mylist)
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    lines[0],
                    LinePlot.Connection(Color(0xFF46468A), 1.dp),
                    LinePlot.Intersection(Color(0xFF46468A), 4.dp),
                    LinePlot.Highlight(Color.Black, 2.dp),
                    LinePlot.AreaUnderLine(Color(0xffDEDEFA), 0.3f)
                ),
                LinePlot.Line(
                    lines[0],
                    LinePlot.Connection(Color(0xFF46468A), 2.dp),
                    LinePlot.Intersection { center, _ ->
                        val px = 2.dp.toPx()
                        val topLeft = Offset(center.x - px, center.y - px)
                        drawRect(Color(0xFF46468A), topLeft, Size(px * 2, px * 2))
                    },
                ),
            ),
            selection =  LinePlot.Selection(
                highlight = LinePlot.Connection(
                    Color.Black,
                    strokeWidth = 1.dp,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 5f))
                ),
                detectionTime = 30
            ),
            xAxis = LinePlot.XAxis(stepSize = mylist.size.dp, steps = 1),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        onSelection = { xLine, points ->

        }
    )
}

