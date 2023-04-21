package com.example.codemaster.ui.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.codemaster.components.BottomNav
import com.example.codemaster.components.BottomNavGraph
import com.example.codemaster.data.model.Codechef
import com.example.codemaster.navigation.BottomNavScreens
import com.example.codemaster.navigation.NavigationGraph
import com.example.codemaster.navigation.Screens
import com.example.codemaster.ui.screens.codechef.CodechefViewModel
import com.example.codemaster.ui.screens.login.LoginViewModel
import com.example.codemaster.utils.NavigateUI
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onNavigate: (route: Screens)-> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect {
            when(it) {
                is NavigateUI.Navigate -> {
                    onNavigate(it.onNavigate)
                }
                is NavigateUI.Snackbar -> {

                }
                is NavigateUI.PopBackStack-> {

                }
            }
        }
    }
//    val navController = rememberNavController()
//    NavigationGraph(navController)
//
//    val bottomNavigationItems = listOf(
//        BottomNavScreens.ContestsScreen,
//        BottomNavScreens.CodechefScreen,
//        BottomNavScreens.CodeforcesScreen,
//        BottomNavScreens.LeetcodeScreen
//    )
//    Scaffold(
//        bottomBar = { BottomNav(navController = navController, items = bottomNavigationItems ) },
//    ) {
//        BottomNavGraph(navController)
//    }
    Column {
        Button(
            onClick = {
                scope.launch {
                    viewModel.logout()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 3.dp, end = 3.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "Log out",
                color = Color.White,
                modifier = Modifier
                    .padding(7.dp),
                fontSize = 16.sp
            )
        }
        Button(
            onClick = {
                scope.launch {
                    viewModel.onEvent(NavigateUI.Navigate(Screens.CodechefScreen))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 3.dp, end = 3.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "codechef",
                color = Color.White,
                modifier = Modifier
                    .padding(7.dp),
                fontSize = 16.sp
            )
        }
        Button(
            onClick = {
                viewModel.onEvent(NavigateUI.Navigate(Screens.CodeforcesScreen))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 3.dp, end = 3.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "codeforces",
                color = Color.White,
                modifier = Modifier
                    .padding(7.dp),
                fontSize = 16.sp
            )
        }
        Button(
            onClick = {
                scope.launch {
                    viewModel.onEvent(NavigateUI.Navigate(Screens.LeetcodeScreen))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 3.dp, end = 3.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "leetcode",
                color = Color.White,
                modifier = Modifier
                    .padding(7.dp),
                fontSize = 16.sp
            )
        }
        Button(
            onClick = {
                scope.launch {
                    viewModel.onEvent(NavigateUI.Navigate(Screens.ContestsScreen))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 3.dp, end = 3.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "contests",
                color = Color.White,
                modifier = Modifier
                    .padding(7.dp),
                fontSize = 16.sp
            )
        }
    }
}