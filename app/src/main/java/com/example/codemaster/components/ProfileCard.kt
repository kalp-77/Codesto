package com.example.codemaster.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.codemaster.R
import com.example.codemaster.ui.theme.Black20
import com.example.codemaster.ui.theme.BlueG
import com.example.codemaster.ui.theme.Codeforces10
import com.example.codemaster.ui.theme.Divider10
import com.example.codemaster.ui.theme.GreenG
import com.example.codemaster.ui.theme.Grid10
import com.example.codemaster.ui.theme.Mindaro20
import com.example.codemaster.ui.theme.Purple80
import com.example.codemaster.ui.theme.RedG
import com.example.codemaster.ui.theme.ThemeBlack30
import com.example.codemaster.ui.theme.ThemeLavender

@Composable
fun ProfileCard(
    avatar: Any,
    handle: String,
    rating: String,
    maxRating: String,
    platformIcon: Int,
    rank: String,
    platform: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.6f),
                            text = "Analysis Your Progress \nTrack Your Rating \nChange",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            color = Grid10,
                            fontFamily = FontFamily(Font(R.font.montserrat)),
                            lineHeight = 20.sp,
                        )
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                modifier = Modifier.align(Alignment.Center).fillMaxWidth().size(25.dp),
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

//@Preview(showBackground = true)
//@Composable
//fun Preview() {
//
//}
//Column(
//modifier = Modifier
////                .padding(15.dp)
//.fillMaxWidth()
//.wrapContentHeight()
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .background(Color.Transparent),
//        shape = RoundedCornerShape(5.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    brush = Brush.run {
//                        linearGradient(
//                            colors = listOf(
//                                com.example.codemaster.ui.theme.BlockCard,
//                                BlueG,
//                            )
//                        )
//                    }
//                )
//                .padding(10.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight()
//            ) {
//                Box(
//                    modifier = Modifier.size(30.dp)
//                ) {
//                    Image(
//                        painter = painterResource(id = platformIcon),
//                        contentDescription = null
//                    )
//                }
//                Spacer(modifier = Modifier.width(8.dp))
//                Column(
//                    modifier = Modifier.height(30.dp),
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Text(
//                        modifier = Modifier,
//                        text = platform,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 10.sp
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(30.dp))
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    text = rank,
//                    fontSize = 16.sp,
//                    letterSpacing = 2.sp,
//                    fontWeight = FontWeight.Medium
//                )
//                Spacer(modifier = Modifier.height(5.dp))
//                Text(
//                    text = rating,
//                    fontSize = 32.sp,
//                    letterSpacing = 2.sp,
//                    fontWeight = FontWeight.ExtraBold
//                )
//
//            }
//
//            Spacer(modifier = Modifier.height(30.dp))
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    modifier = Modifier,
//                    text = "@${handle}",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 10.sp
//                )
//                Text(
//                    modifier = Modifier,
//                    text = "Max Rating - $maxRating",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 10.sp
//                )
//            }
//        }
//    }
//}
