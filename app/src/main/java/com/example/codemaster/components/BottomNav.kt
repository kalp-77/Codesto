package com.example.codemaster.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.codemaster.navigation.BottomNavScreens
import com.example.codemaster.navigation.Screens
import com.example.codemaster.ui.screens.codechef.CodechefScreen
import com.example.codemaster.ui.screens.codeforces.CodeforcesScreen
import com.example.codemaster.ui.screens.leetcode.LeetcodeScreen

@Composable
fun BottomNav(
    navController: NavHostController,
    items: List<BottomNavScreens>
) {
    var selectedItem by remember { mutableStateOf(0) }
    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 30.dp,
        modifier = Modifier
            .height(52.dp),
        contentColor = Color.Gray,
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
//                selected = selectedItem == index,
                icon = { Icon(painterResource(id = item.icon), contentDescription = null) },
//                onClick = { selectedItem = index },
                selectedContentColor = Color(0xFF2A265C),
                unselectedContentColor = Color.LightGray,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController= navController,
        startDestination = BottomNavScreens.ContestsScreen.route
    ){
        composable(BottomNavScreens.ContestsScreen.route){
            TabView()
        }
        composable(BottomNavScreens.CodechefScreen.route){
            CodechefScreen()
        }
        composable(BottomNavScreens.CodeforcesScreen.route){
            CodeforcesScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable(BottomNavScreens.LeetcodeScreen.route){
            LeetcodeScreen()
        }
    }
}