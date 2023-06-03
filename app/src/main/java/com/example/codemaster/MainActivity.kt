package com.example.codemaster

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.codemaster.components.BottomNav
import com.example.codemaster.components.NavDrawer.AnimatedDrawer
import com.example.codemaster.components.NavDrawer.DrawerBody
import com.example.codemaster.components.NavDrawer.rememberDrawerState
import com.example.codemaster.components.TopBar
import com.example.codemaster.navigation.NavigationGraph
import com.example.codemaster.navigation.Screens
import com.example.codemaster.ui.screens.codechef.CodechefScreen
import com.example.codemaster.ui.screens.codeforces.CodeforcesScreen
import com.example.codemaster.ui.screens.leetcode.LeetcodeScreen
import com.example.codemaster.ui.theme.CodeMasterTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeMasterTheme(
                darkTheme = true,
                dynamicColor = true
            ) {
//                 A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF131314),
                    shadowElevation = 0.dp
                ) {
                    DrawerContent()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DrawerContent(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    var showTopBar by rememberSaveable { mutableStateOf(true) }

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        Screens.LoginScreen.name -> false
        Screens.SignupScreen.name -> false
        Screens.PlatformScreen.name -> false
        Screens.ProblemsetScreen.name -> false
        Screens.RatingChangeScreen.name -> false
        Screens.CodeforcesScreen.name-> false
        Screens.CodechefScreen.name -> false
        Screens.LeetcodeScreen.name -> false
        Screens.FriendsScreen.name-> false
        else -> true
    }

    showTopBar = when(navBackStackEntry?.destination?.route){
        Screens.RatingChangeScreen.name -> false
        Screens.CodeforcesScreen.name-> false

        else -> true
    }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    //handle drawer width on open
    val drawerState = rememberDrawerState(drawerWidth = 250.dp)

    AnimatedDrawer(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        state = drawerState,
        drawerContent = {
            DrawerBody (
                scope = scope,
                navController = navController,
                modifier = Modifier.fillMaxSize(),
                onCloseClick = {
                    scope.launch { drawerState.close() }
                },
                drawerState = drawerState
            )
        },
        content = {
            Scaffold(
                backgroundColor = Color.Black,
                scaffoldState = scaffoldState,
                topBar = {
                    if(showTopBar) {
                        TopBar(
                            onOpenClick = {
                                scope.launch { drawerState.open() }
                            }
                        )
                    }
                },
//                bottomBar = { if (showBottomBar) BottomNav(navController = navController) },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(it)
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { change, dragAmount ->
                                if (dragAmount > 10) {
                                    scope.launch { drawerState.open() }
                                }
                                if (dragAmount < 0) {
                                    scope.launch { drawerState.close() }
                                }
                                change.consume()
                            }
                        }
                ) {
                    NavigationGraph(navController = navController)
                }
            }
        }
    )
}