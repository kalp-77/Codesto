package com.example.codemaster.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codemaster.ui.screens.contests.ContestsScreen
import com.example.codemaster.ui.screens.contests.FutureContestsScreen
import com.example.codemaster.ui.screens.contests.OngoingContestsScreen
import com.example.codemaster.ui.screens.leetcode.font
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabView(
//    topBar : @Composable ()->Unit,
//    intent : Intent
){
    val pagerState = rememberPagerState(0)
    Column(
        modifier = Modifier.background(Color.White)
    ) {
//        topBar()
        Tabs(pagerState = pagerState)
        TabsContent(
            pagerState = pagerState,
//            intent = intent
        )
    }
}

// Tabs
@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        "Ongoing",
        "In 24 hrs",
        "Future"
    )
    val scope = rememberCoroutineScope()

    // Tab row
    androidx.compose.material3.ScrollableTabRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Unspecified),
        selectedTabIndex = pagerState.currentPage,
        containerColor = Color.White,
        edgePadding = 24.dp,
        contentColor = Color.Black,
        indicator = {
            Box(
                Modifier
                    .tabIndicatorOffset(it[pagerState.currentPage])
                    .height(height = 1.dp)
                    .padding(start = 14.dp, end = 14.dp)
                    .background(color = Color.Black)
            )
        },
        divider = {
            androidx.compose.material3.Divider(
                color = Color.White
            )
        }
    ) {
        list.forEachIndexed { index, _ ->
            androidx.compose.material3.Tab(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(vertical = 2.dp),
                text = {
                    Text(
                        text = list[index],
                        color = if (pagerState.currentPage == index) Color(0xFF2A265C) else Color.LightGray,
                        fontSize = 14.sp,
                        fontFamily = font
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalPagerApi
@Composable
fun TabsContent(
    pagerState: PagerState,
//    intent : Intent
) {
    HorizontalPager(state = pagerState, count = 3) { page ->
        when (page) {
            0 -> OngoingContestsScreen()
            1 -> ContestsScreen()
            2 -> FutureContestsScreen()
        }
    }
}
