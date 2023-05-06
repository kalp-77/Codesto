package com.example.codemaster.ui.screens.codechef

import android.content.Intent
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
import com.example.codemaster.components.HorizontalCarousel
import com.example.codemaster.components.graphs.LineChart
import com.example.codemaster.components.ProfileCard
import com.example.codemaster.ui.theme.Black20
import com.example.codemaster.ui.theme.BlueG
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                        .background(Black20)
                ) {
                    ProfileCard(
                        avatar = state.data.avatar,
                        handle = state.data.username,
                        rating = state.data.rating,
                        maxRating = state.data.max_rating,
                        platformIcon = R.drawable.codechef,
                        rank = state.data.div,
                        platform = "CODECHEF"
                    )
                    Spacer(modifier = Modifier.height(30.dp))
//                    BlockCard(
//                        block1 = "Problemset",
//                        block2 = state.data.stars,
//                        blockColor1 =  BlueG,
//                        blockColor2 = GreenG,
//                        blockTextColor1 = Color.Black,
//                        blockTextColor2 = viewModel.starColor.value,
//                        icon1 = R.drawable.codechef,
//                        icon2 = R.drawable.codechef,
//                        onClickBlock1 = {
//                            val myIntent =
//                                Intent(MyApplication.instance, WebViewActivity::class.java)
//                            val url =
//                                "https://www.codechef.com/practice?end_rating=999&group=all&hints=0&itm_campaign=problems&itm_medium=home&limit=20&page=0&search=&sort_by=difficulty_rating&sort_order=asc&start_rating=0&tags=&topic=&video_editorial=0&wa_enabled=0"
//                            myIntent.putExtra("key", url)
//                            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            MyApplication.instance.startActivity(myIntent)
//                        },
//                        onClickBlock2 = {  }
//                    )
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
                            size = viewModel.graphDataState.value.dataPoints.size
                        )
                    }
                }
            }
        }
    }
}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CodechefDisplayScreen(
//    data : Codechef
//){
//    Box(
//        modifier = Modifier
//            .background(Color(0xFFEEF0FD))
//            .fillMaxSize()
//    ){
//        Column(modifier = Modifier.padding(10.dp)) {
//            Row(modifier = Modifier.padding(10.dp)) {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    colors = CardDefaults.cardColors(Color.White),
//                    elevation = CardDefaults.cardElevation(3.dp),
//                    shape = RoundedCornerShape(10.dp),
//                ) {
//                    Column(modifier = Modifier.padding(10.dp)) {
//                        Row {
//                            Column(modifier = Modifier.padding(10.dp)) {
//                               val painter = rememberImagePainter(data = data.avatar)
//                                Image(
//                                    painter = painter,
//                                    contentDescription = "Profile_picture",
//                                    modifier = Modifier
//                                        .size(80.dp)
//                                )
//                            }
//                            Column(modifier = Modifier.padding(10.dp)) {
//                                Text(
//                                    text = "@${data.username}",
//                                    fontWeight = FontWeight.ExtraBold,
//                                    color = Color(0xFF2A265C),
//                                    fontFamily = font,
//                                    fontSize = 15.sp,
//                                )
//                                Row {
//                                    Text(
//                                        text = data.div,
//                                        modifier = Modifier.padding(end = 15.dp),
//                                        fontFamily = font,
//                                        color = Color(0xFF2A265C),
//                                    )
//                                    Text(
//                                        text = data.stars,
//                                        color =
//                                        if (data.rating < "1400") Color(0xFF666666)
////                                        else if (data.rating < "1600" && data.rating >= "1400")
////                                            Color(0xFF1E7D22)
////                                        else if (data.rating < "1800" && data.rating >= "1600")
////                                            Color(0xFF3366CB)
////                                        else if (data.rating < "2000" && data.rating >= "1800")
////                                            Color(0xFF684273)
////                                        else if (data.rating < "2200" && data.rating >= "2000")
////                                            Color(0xFFFEBE00)
////                                        else if (data.rating < "2500" && data.rating >= "2200")
////                                            Color(0xFFFE7F00)
////                                        else
////                                            Color.Red,
//                                        fontFamily = font
//                                    )
//                                }
//                                Text(
//                                    text = "Max Rating: ${data.max_rating}",
//                                    fontFamily = font,
//                                    color = Color(0xFF2A265C),
//                                )
//                                Text(
//                                    text = "Rating: ${data.rating}",
//                                    fontFamily = font,
//                                    color = Color(0xFF2A265C),
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//            Row(modifier = Modifier.padding(10.dp)){
//                val url = "https://www.codechef.com/practice?end_rating=999&group=all&hints=0&itm_campaign=problems&itm_medium=home&limit=20&page=0&search=&sort_by=difficulty_rating&sort_order=asc&start_rating=0&tags=&topic=&video_editorial=0&wa_enabled=0"
//                Card(
//                    modifier = Modifier
//                        .height(80.dp)
//                        .fillMaxWidth(),
//                    colors = CardDefaults.cardColors(Color.White),
//                    onClick = {
//                        val myIntent = Intent(MyApplication.instance, WebViewActivity::class.java)
//                        myIntent.putExtra("key", url)
//                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        MyApplication.instance.startActivity(myIntent)
//                    },
//                    shape = RoundedCornerShape(10.dp),
//                    elevation = CardDefaults.cardElevation(3.dp)
//                ) {
//                    Text(
//                        text = "PROBLEMS",
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.wrapContentSize(),
//                        fontFamily = font,
//                        color = Color(0xFF2A265C),
//                    )
//                }
//            }
//            Row(modifier = Modifier.padding(10.dp)) {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    elevation = CardDefaults.cardElevation(3.dp),
//                    shape = RoundedCornerShape(10.dp)
//                ) {
//                    CodechefGraph(data = data)
//                }
//            }
//        }
//    }
//}

//@Composable
//fun CodechefGraph(data:Codechef){
//    val mylist = mutableListOf<DataPoint>()
//    var i = 0
//    for(j in data.contests) {
//        mylist.add(DataPoint(i.toFloat(), j.toFloat()))
//        i += 1
//    }
//
//    val lines: List<List<DataPoint>> = listOf(mylist)
//    LineGraph(
//        plot = LinePlot(
//            listOf(
//                LinePlot.Line(
//                    lines[0],
//                    LinePlot.Connection(Color(0xFF46468A), 1.dp),
//                    LinePlot.Intersection(Color(0xFF46468A), 4.dp),
//                    LinePlot.Highlight(Color.Black, 4.dp),
//                    LinePlot.AreaUnderLine(Color(0xffDEDEFA), 0.3f)
//                ),
//                LinePlot.Line(
//                    lines[0],
//                    LinePlot.Connection(Color(0xFF46468A), 2.dp),
//                    LinePlot.Intersection { center, _ ->
//                        val px = 2.dp.toPx()
//                        val topLeft = Offset(center.x - px, center.y - px)
//                        drawRect(Color(0xFF46468A), topLeft, Size(px * 2, px * 2))
//                    },
//                ),
//            ),
//            selection =  LinePlot.Selection(
//                highlight = LinePlot.Connection(
//                    Color.Black,
//                    strokeWidth = 3.dp,
//                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 20f))
//                ),
//                detectionTime = 50
//            ),
//            xAxis = LinePlot.XAxis(stepSize = mylist.size.dp, steps = 1),
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(300.dp)
//    )
//}

