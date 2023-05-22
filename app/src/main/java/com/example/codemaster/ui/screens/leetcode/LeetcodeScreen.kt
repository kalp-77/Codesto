package com.example.codemaster.ui.screens.leetcode

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.R
import com.example.codemaster.components.graphs.LinearProgressIndicatorSample
import com.example.codemaster.data.model.Leetcode
import com.example.codemaster.ui.theme.Grid10
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

val font = FontFamily.SansSerif

@Composable
fun LeetcodeScreen(
    leetcodeViewModel: LeetcodeViewModel = hiltViewModel()
){
    val heightInPx = with(LocalDensity.current) { LocalConfiguration.current
        .screenHeightDp.dp.toPx()
    }
    Column(
        modifier = Modifier
            .background(Color.Black)
    ) {
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

@Composable
fun LeetcodeDisplayScreen(
    data : Leetcode
){
    //for bounce animation
    val offsetY = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .background(Color(0xFF131313))
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .padding(25.dp)
                    //bounce animation
                    .offset { IntOffset(0, offsetY.value.roundToInt()) }
                    .pointerInput(Unit) {
                        forEachGesture {
                            awaitPointerEventScope {
                                //Detect a touch down event
                                awaitFirstDown()
                                do {
                                    val event: PointerEvent = awaitPointerEvent()
                                    event.changes.forEach { pointerInputChange: PointerInputChange ->
                                        //Consume the change
                                        scope.launch {
                                            offsetY.snapTo(
                                                offsetY.value + pointerInputChange.positionChange().y
                                            )
                                        }
                                    }
                                } while (event.changes.any { it.pressed })

                                // Touch released - Action_UP
                                scope.launch {
                                    offsetY.animateTo(
                                        targetValue = 0f, spring(
                                            dampingRatio = Spring.DampingRatioLowBouncy, // to handle bounce
                                            stiffness = Spring.StiffnessLow     //to handle speed

                                        )
                                    )
                                }

                            }
                        }
                    }
            ) {
                Row(modifier = Modifier,
                    verticalAlignment = Alignment.Bottom
                ){
                    Text(
                        text = "LEETCODE",
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                //problem count
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.Start
                ){
                    Text(
                        text = data.total_problems_solved,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentSize(),
                        color = Color.White,
                        fontSize = 35.sp
                    )
                    Text(
                        text = "Problem Count",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentSize().padding(top = 10.dp),
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )

                }
                Spacer(modifier = Modifier.height(30.dp))

                //progress indicators
                Row(modifier = Modifier){
                    Column {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(Color(0xFF000000)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Column(modifier = Modifier.padding(bottom = 10.dp)) {
                                    Box {
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                        ){
                                            Text(
                                                text = "Easy",
                                                color = Color.Gray,
                                                fontSize = 13.sp,
                                            )
                                            Text(
                                                text = buildAnnotatedString {
                                                    pushStyle(SpanStyle(Color.White, fontSize = 14.sp))
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
                                    Box {
                                        LinearProgressIndicatorSample(
                                            que = data.easy_questions_solved.toFloat(),
                                            total_que = data.total_easy_questions.toFloat(),
                                            color = Color(0xFF00B7A2),
                                            trackColor = Color(0xFF294C34)
                                        )
                                    }
                                    Box(modifier = Modifier.padding(top = 10.dp)) {
                                        Row (
                                            modifier = Modifier.fillMaxWidth()

                                        ){
                                            Text(
                                                text = "Medium",
                                                color = Color.Gray,
                                                fontSize = 13.sp,
                                            )
                                            Text(
                                                text = buildAnnotatedString {
                                                    pushStyle(SpanStyle(Color.White, fontSize = 14.sp))
                                                    append(data.medium_questions_solved)
                                                    pushStyle(SpanStyle(Color.Gray, fontSize = 13.sp))
                                                    append("/${data.total_medium_questions}")
                                                    pop()
                                                },
                                                textAlign = TextAlign.End,
                                                modifier = Modifier.padding(start = 15.dp),
                                            )
                                        }
                                    }
                                    Box {
                                        LinearProgressIndicatorSample(
                                            que = data.medium_questions_solved.toFloat(),
                                            total_que = data.total_medium_questions.toFloat(),
                                            color = Color(0xFFFEBF1E),
                                            trackColor = Color(0xFF5F4E27)
                                        )
                                    }
                                    Box(modifier = Modifier.padding(top = 10.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "Hard",
                                                color = Color.Gray,
                                                fontSize = 13.sp
                                            )
                                            Text(
                                                text = buildAnnotatedString {
                                                    pushStyle(SpanStyle(Color.White, fontSize = 14.sp))
                                                    append(data.hard_questions_solved)
                                                    pushStyle(SpanStyle(Color.Gray, fontSize = 13.sp))
                                                    append("/${data.total_hard_questions}")
                                                    pop()
                                                },
                                                textAlign = TextAlign.End,
                                                modifier = Modifier.padding(start = 15.dp),
                                            )
                                        }
                                    }
                                    Box {
                                        LinearProgressIndicatorSample(
                                            que = data.hard_questions_solved.toFloat(),
                                            total_que = data.total_hard_questions.toFloat(),
                                            color = Color(0xFFEE4743),
                                            trackColor = Color(0xFF5A302F)
                                        )
                                    }
                                }
                                Text(
                                    text = "Max Rank: ${data.ranking}",
                                    color = Color.LightGray,
                                    fontFamily = FontFamily.Serif,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))


                //problemset
                Column( modifier = Modifier
                    .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ){
                    val url = "https://leetcode.com/problemset/all/"
                    Text(
                        text = "Explore Problems",
                        color = Color.LightGray,
                        fontSize = 13.sp
                    )
                    Row (
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Card(
                            modifier = Modifier
                                .fillMaxHeight(0.33f)
                                .weight(1f),
                            colors = CardDefaults.cardColors(Color(0xff1F2720)),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxSize()
                            ){
                                Box(contentAlignment = Alignment.Center){
                                    Text(
                                        text = "Easy",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.wrapContentSize(),
                                        color = Color.White,
                                        fontSize = 13.sp

                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxHeight(0.33f)
                                .weight(1f),
                            colors = CardDefaults.cardColors(Color(0xFF45432F)),
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(5.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxSize()
                            ){
                                Text(
                                    text = "Medium",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.wrapContentSize(),
                                    color = Color.White,
                                    fontSize = 13.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxHeight(0.33f)
                                .weight(1f),
                            colors = CardDefaults.cardColors(Color(0xFF481D1D)),
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(5.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxSize()
                            ){
                                Text(
                                    text = "Hard",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.wrapContentSize(),
                                    color = Color.White,
                                    fontSize = 13.sp

                                )
                            }
                        }
                    }
                }
            }

            //banner
            Row (
                modifier = Modifier
                    .fillMaxSize()
                ,
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xff0B0A0A))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Column(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Unlock Your Potential \n" +
                                    "Conquer leetcode with confidence!!",
                            color = Color(0xff8E8B8B),
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    Column(
                        modifier = Modifier
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.astronaut),
                            contentDescription = "banner_img",
                            modifier = Modifier.size(70.dp)
                        )
                    }
                }
            }
        }

    }
}