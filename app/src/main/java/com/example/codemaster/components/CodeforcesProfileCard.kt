package com.example.codemaster.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codemaster.R
import com.example.codemaster.ui.theme.Grid10
import com.example.codemaster.ui.theme.ThemeLavender

@Composable
fun ProfileCard(
    avatar: String,
    handle: String,
    rating: String,
    maxRating: String,
    platformIcon: Int,
    rank: String,
    platform: String,
    onNavigation: ()-> Unit
) {
    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Spacer(modifier = Modifier.height(70.dp))
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
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = rating,
                        fontSize = 30.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.montserrat))
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Current Rating",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.LightGray,
                            fontFamily = FontFamily(Font(R.font.montserrat))
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.48f)
                                .clip(RoundedCornerShape(14.dp))
                                .height(50.dp)
                                .background(ThemeLavender),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 15.dp, top = 8.dp, bottom = 8.dp, end = 15.dp),
                                text = rank,
                                fontSize = 10.sp,
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.montserrat)),
                                maxLines = 2,
                                lineHeight = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(50.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color.Black)
                                .border(
                                    BorderStroke(1.dp, ThemeLavender),
                                    shape = RoundedCornerShape(14.dp)
                                ),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .clickable(onClick = onNavigation)
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.wrapContentWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center) {
                                    Text(
                                        modifier = Modifier,
                                        text = "Connect",
                                        fontSize = 10.sp,
                                        color = Color.White,
                                        fontFamily = FontFamily(Font(R.font.montserrat))
                                    )
                                    Text(
                                        modifier = Modifier,
                                        text = "with friends",
                                        fontSize = 10.sp,
                                        color = Color.White,
                                        fontFamily = FontFamily(Font(R.font.montserrat))
                                    )
                                }
                                Box(
                                    modifier = Modifier.size(30.dp)
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(20.dp)
                                            .align(Alignment.Center),
                                        painter = painterResource(id  = R.drawable.star),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Visualize your rating change",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.DarkGray,
                            fontFamily = FontFamily(Font(R.font.montserrat)),
                            lineHeight = 20.sp,
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                        ) {
                            Text(
                                modifier = Modifier.wrapContentWidth(),
                                text = "Track your progress",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.DarkGray,
                                fontFamily = FontFamily(Font(R.font.montserrat)),
                                lineHeight = 20.sp,
                            )
                            Box(
                                modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically)
                            ) {
                                Image(
                                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterStart).size(10.dp),
                                    painter = painterResource(id  = R.drawable.arrow_right),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}