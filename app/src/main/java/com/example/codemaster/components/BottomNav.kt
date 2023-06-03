package com.example.codemaster.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.codemaster.R
import com.example.codemaster.navigation.BottomNavScreens
import com.skydoves.landscapist.rememberDrawablePainter


@Composable
fun BottomNav(
    navController: NavHostController,
) {
    val items = listOf(
        BottomNavScreens.HomeScreen,
        BottomNavScreens.CodeforcesScreen,
        BottomNavScreens.CodechefScreen,
        BottomNavScreens.PlatformScreen
    )
    val currentRoute = remember { mutableStateOf(navController.currentDestination?.route) }
    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 30.dp,
        modifier = Modifier.height(50.dp),
        contentColor = Color.Gray,
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                selectedContentColor = Color(0xFF2A265C),
                unselectedContentColor = Color.LightGray,
                selected = currentRoute.value == item.route,
                onClick = {
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let { screen ->
                            popUpTo(screen) {
                                saveState = false
                            }
                        }
                        restoreState = false
                    }
                    currentRoute.value = item.route
                }
            )
        }
    }
}