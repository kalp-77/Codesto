package com.example.codemaster.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.codemaster.navigation.BottomNavScreens


@Composable
fun BottomNav(
    navController: NavHostController,
) {
    val items = listOf(
        BottomNavScreens.ContestsScreen,
        BottomNavScreens.CodeforcesScreen,
        BottomNavScreens.CodechefScreen,
        BottomNavScreens.LeetcodeScreen
    )
    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 30.dp,
        modifier = Modifier.height(50.dp),
        contentColor = Color.Gray,
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = null,
//                        modifier = Modifier.size(20.dp)
                    )
                },
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