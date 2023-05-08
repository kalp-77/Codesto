package com.example.codemaster

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.codemaster.components.BottomNav
import com.example.codemaster.components.TopBar
import com.example.codemaster.navigation.NavigationGraph
import com.example.codemaster.navigation.Screens
import com.example.codemaster.ui.theme.CodeMasterTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeMasterTheme {
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()
                var showBottomBar by rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                var showTopBar by rememberSaveable { mutableStateOf(true) }

                showBottomBar = when (navBackStackEntry?.destination?.route) {
                    Screens.LoginScreen.name -> false
                    Screens.SignupScreen.name -> false
                    Screens.PlatformScreen.name -> false
                    Screens.ProblemsetScreen.name -> false
                    Screens.RatingChangeScreen.name -> false
                    Screens.CodeforcesScreen.name-> false
                    Screens.CodechefScreen.name-> false
                    else -> true
                }

                showTopBar = when(navBackStackEntry?.destination?.route){
                    Screens.CodeforcesScreen.name -> false
                    Screens.CodechefScreen.name-> false
                    Screens.RatingChangeScreen.name -> false
                    else -> true
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = { if(showTopBar) TopBar() },
                        bottomBar = { if (showBottomBar) BottomNav(navController = navController) }
                    ) {
                        innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            NavigationGraph(navController = navController)
                        }
                    }
                }
            }
        }
    }
}