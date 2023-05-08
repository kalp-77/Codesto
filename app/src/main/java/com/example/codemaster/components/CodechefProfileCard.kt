package com.example.codemaster.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codemaster.R
import com.example.codemaster.ui.theme.Black20
import com.example.codemaster.ui.theme.Darkblack30
import com.example.codemaster.ui.theme.SnackbarBlue30
import com.example.codemaster.ui.theme.ThemeLavender



@OptIn(ExperimentalTextApi::class)
@Composable
fun CodechefProfileCard(
    avatar: Any,
    handle: String,
    rating: String,
    maxRating: String,
    platformIcon: Int,
    div: String,
    stars : String,
    platform: String,
    onClick: ()-> Unit

) {
    Column(
        modifier = Modifier
            .background(Black20)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.wrapContentHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier,
                            text = platform,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color.LightGray
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(0.6f)
                        ) {
                            Text(
                                modifier = Modifier.wrapContentWidth(),
                                text = rating,
                                fontSize = 30.sp,
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.montserrat))
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = "Current Rating",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.montserrat))
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth()
                            .padding(start = 40.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(47.dp)
                                    .clip(CircleShape)
                                    .background(ThemeLavender),
                                contentAlignment = Alignment.Center
                            ){
                                Row(
                                    modifier = Modifier.wrapContentSize(),
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Text(
                                        text = stars,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
//                                    Box(modifier = Modifier.wrapContentSize(),
//                                        contentAlignment = Alignment.TopCenter
//                                    ) {
//                                        Image(
//                                            modifier = Modifier.size(17.dp),
//                                            painter = painterResource(id = R.drawable.rating_star),
//                                            contentDescription = null,
//                                            colorFilter = ColorFilter.tint(Color.White)
//                                        )
//                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.48f)
                                .clip(RoundedCornerShape(8.dp))
                                .height(80.dp)
                                .background(Darkblack30)
                                .clickable(
                                    onClick = onClick,
                                    enabled = true
                                ),
                        ) {
                            Text(
                                modifier = Modifier.padding(15.dp).align(Alignment.BottomStart),
                                text = "Explore \nProblems",
                                fontSize = 14.sp,
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.montserrat)),
                                maxLines = 2,
                                lineHeight = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Box(modifier = Modifier.wrapContentSize().padding(20.dp).align(Alignment.TopEnd),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Image(
                                    modifier = Modifier.size(17.dp),
                                    painter = painterResource(id = R.drawable.arrow_right),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .height(80.dp)
                                .background(Darkblack30),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    modifier = Modifier.padding(start = 20.dp),
                                    text = div[4].toString(),
                                    style = TextStyle.Default.copy(
                                        fontSize = 54.sp,
                                        fontFamily = FontFamily(Font(R.font.montserrat)),
                                        drawStyle = Stroke(
                                            miter = 10f,
                                            width = 5f,
                                            join = StrokeJoin.Round
                                        ),
                                        color = ThemeLavender
                                    )
                                )
                                Text(
                                    modifier = Modifier.padding(start = 3.dp, bottom = 10.dp),
                                    text = "DIV",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontFamily = FontFamily(Font(R.font.montserrat)),
                                    maxLines = 2,
                                    lineHeight = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}