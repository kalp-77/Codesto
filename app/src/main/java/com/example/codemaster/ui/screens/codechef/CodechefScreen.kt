package com.example.codemaster.ui.screens.codechef

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.MyApplication
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot

//val font = FontFamily(Font(R.font.varelaround_regular))

@Composable
fun CodechefScreen(){
    Box(
        modifier = Modifier
            .background(Color(0xFFEEF0FD))
            .fillMaxSize()
    ){
        Column(modifier = Modifier.padding(10.dp)) {
            Row(modifier = Modifier.padding(10.dp)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = 5.dp,
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row() {
                            Column(modifier = Modifier.padding(10.dp)) {
                               val painter = rememberImagePainter(data = data.avatar)
                                Image(
                                    painter = painter,
                                    contentDescription = "Profile_picture",
                                    modifier = Modifier
                                        .size(80.dp)
                                )
                            }
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "@${data.username}",
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF2A265C),
                                    fontFamily = font,
                                    fontSize = 15.sp,
                                )
                                Row {
                                    Text(
                                        text = data.div,
                                        modifier = Modifier.padding(end = 15.dp),
                                        fontFamily = font,
                                        color = Color(0xFF2A265C),
                                    )
                                    Text(
                                        text = data.stars,
                                        color =
                                        if (data.rating < "1400") Color(0xFF666666)
                                        else if (data.rating < "1600" && data.rating >= "1400")
                                            Color(0xFF1E7D22)
                                        else if (data.rating < "1800" && data.rating >= "1600")
                                            Color(0xFF3366CB)
                                        else if (data.rating < "2000" && data.rating >= "1800")
                                            Color(0xFF684273)
                                        else if (data.rating < "2200" && data.rating >= "2000")
                                            Color(0xFFFEBE00)
                                        else if (data.rating < "2500" && data.rating >= "2200")
                                            Color(0xFFFE7F00)
                                        else
                                            Color.Red,
                                        fontFamily = font
                                    )
                                }
                                Text(
                                    text = "Max Rating: ${data.max_rating}",
                                    fontFamily = font,
                                    color = Color(0xFF2A265C),
                                )
                                Text(
                                    text = "Rating: ${data.rating}",
                                    fontFamily = font,
                                    color = Color(0xFF2A265C),
                                )
                            }
                        }
                    }
                }
            }
            Row(modifier = Modifier.padding(10.dp)){
                val url = "https://www.codechef.com/practice?end_rating=999&group=all&hints=0&itm_campaign=problems&itm_medium=home&limit=20&page=0&search=&sort_by=difficulty_rating&sort_order=asc&start_rating=0&tags=&topic=&video_editorial=0&wa_enabled=0"
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
                    elevation = 5.dp
                ) {
                    Text(
                        text = "PROBLEMS",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentSize(),
                        fontFamily = com.example.codemaster.ui.leetcode_screen.font,
                        color = Color(0xFF2A265C),
                    )
                }
            }
            Row(modifier = Modifier.padding(10.dp)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = 5.dp,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    CCGraph(data = data)
                }
            }
        }
    }
}

@Composable
fun CCGraph(data:Codechef){
    val mylist = mutableListOf<DataPoint>()
    var i = 0
    for(j in data.contests) {
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
                    LinePlot.Highlight(Color.Black, 4.dp),
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
                    strokeWidth = 3.dp,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 20f))
                ),
                detectionTime = 50
            ),
            xAxis = LinePlot.XAxis(stepSize = mylist.size.dp, steps = 1),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}

@Composable
fun Setdetail(
    topBar : @Composable ()->Unit,
    codechefViewModel: CodechefViewModel = hiltViewModel()
) {
    val heightInPx = with(LocalDensity.current) { LocalConfiguration.current
        .screenHeightDp.dp.toPx()
    }
    Column {
        topBar()
        when (val state = codechefViewModel.uiState.collectAsState().value) {
            is CodechefUiState.Empty -> Text(
                text = "No data available",
                modifier = Modifier.padding(16.dp)
            )
            is CodechefUiState.Loading ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFFB3BCF8)
                    )
                }
            is CodechefUiState.Failure -> ErrorDialog(state.message)
            is CodechefUiState.Success -> CodechefScreen(state.data)
        }
    }
}