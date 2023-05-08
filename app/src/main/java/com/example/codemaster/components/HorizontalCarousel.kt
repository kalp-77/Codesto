package com.example.codemaster.components

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codemaster.R
import com.example.codemaster.ui.theme.PurpleNavy20
import com.example.codemaster.ui.theme.PurpleNavy30
import com.example.codemaster.ui.theme.ThemeBlack30


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalCarousel(){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display 10 items
        val pageCount = 3
        val pagerState = rememberPagerState()
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            pageCount = pageCount,
            state = pagerState,
            userScrollEnabled = true
        ) { page ->
            CarouselCard {

            }
        }
        Row(
            Modifier
                .height(18.dp)
                .padding(start = 4.dp)
                .width(50.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { iteration ->
                val lineWeight = animateFloatAsState(
                    targetValue = if (pagerState.currentPage == iteration) {
                        1.5f
                    } else {
                        if (iteration < pagerState.currentPage) {
                            0.5f
                        } else {
                            1f
                        }
                    }, label = "weight", animationSpec = tween(300, easing = EaseInOut)
                )
                val color =
                    if (pagerState.currentPage == iteration) Color.White else Color.White.copy(alpha = 0.5f)
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(color)
                        .weight(lineWeight.value)
                        .height(4.dp)
                )
            }
        }
    }
}


@Composable
fun CarouselCard(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ThemeBlack30)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Learn Practice \nProblem Solving",
                fontSize = 16.sp,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth(0.65f),
                lineHeight = 20.sp,
                fontStyle = FontStyle.Italic
            )
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Learn",
                    color = Color.Black,
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp
                )
            }
        }
    }

}