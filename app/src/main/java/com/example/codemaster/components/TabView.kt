package com.example.codemaster.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.codemaster.ui.screens.home.FutureContestsScreen
import com.example.codemaster.ui.screens.home.HomeScreen
import com.example.codemaster.ui.screens.home.OngoingContestsScreen
import com.example.codemaster.ui.screens.leetcode.font
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch


private val TABS_LIST = listOf(
    "Ongoing",
    "In 24 hrs",
    "Future"
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabView(){
    val pagerState = rememberPagerState(0)
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        Tabs(pagerState = pagerState)
        TabsContent(
            pagerState = pagerState,
        )
    }
}

// Tabs
@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    val tabIndicator: @Composable (List<TabPosition>) -> Unit = {
        Box(
            Modifier
                .tabIndicatorOffset(it[pagerState.currentPage])
                .height(height = 1.dp)
                .padding(start = 14.dp, end = 14.dp)
                .background(color = Color.Black)
        )
    }
    val divider: @Composable () -> Unit = {
        Divider(
            color = Color.White
        )
    }
    ScrollableTabRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Unspecified),
        selectedTabIndex = pagerState.currentPage,
        containerColor = Color.White,
        edgePadding = 24.dp,
        contentColor = Color.Black,
        indicator = tabIndicator,
        divider = divider
    ) {
        TABS_LIST.forEachIndexed { index, _ ->
            Tab(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(vertical = 2.dp),
                text = {
                    Text(
                        text = TABS_LIST[index],
                        fontSize = 14.sp,
                        fontFamily = font
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.scrollToPage(index)
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
    pagerState: PagerState
) {
    HorizontalPager(state = pagerState, count = 3) { page ->
        when (page) {
            0 ->{
                OngoingContestsScreen()
            }
            1 ->{
                HomeScreen()
            }
            2 ->{
                FutureContestsScreen()
            }
        }
    }

}