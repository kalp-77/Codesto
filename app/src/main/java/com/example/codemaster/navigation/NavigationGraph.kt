package com.example.codemaster.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.codemaster.components.TabView
import com.example.codemaster.ui.screens.codechef.CodechefScreen
import com.example.codemaster.ui.screens.codeforces.CodeforcesScreen
import com.example.codemaster.ui.screens.codeforces.FriendsScreen
import com.example.codemaster.ui.screens.codeforces_problemset.ProblemsetScreen
import com.example.codemaster.ui.screens.codeforces_ratingchange.RatingChangeScreen
import com.example.codemaster.ui.screens.home.HomeScreen
import com.example.codemaster.ui.screens.leetcode.LeetcodeScreen
import com.example.codemaster.ui.screens.login.LoginScreen
import com.example.codemaster.ui.screens.platform.PlatformScreen
import com.example.codemaster.ui.screens.platform.PlatformViewModel
import com.example.codemaster.ui.screens.signup.SignupScreen

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("NewApi")
@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: PlatformViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen.name
    ) {
        composable(route = Screens.LoginScreen.name) {
            LoginScreen(
                onNavigate = {
                    navController.navigate(it.name) {
                        popUpTo(it.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screens.SignupScreen.name) {
            SignupScreen(
                onNavigate = {
                    navController.navigate(it.name){
                        popUpTo(it.name){ inclusive = true }
                    }
                }
            )
        }
        composable(route = Screens.PlatformScreen.name) {
            PlatformScreen(
                onNavigate = {
                    navController.navigate(it.name) {
                        popUpTo(it.name) {
                            inclusive = true
                        }
                    }

                }
            )
        }
        composable(route = Screens.CodechefScreen.name) {
            CodechefScreen()
        }
        composable(route = Screens.CodeforcesScreen.name) {
            CodeforcesScreen(
                onNavigate = {
                    navController.navigate(it.name)
                },
                navController
            )
        }
        composable(route = Screens.ProblemsetScreen.name) {
            ProblemsetScreen()
        }
        composable(route = Screens.RatingChangeScreen.name) {
            RatingChangeScreen()
        }
        composable(route = Screens.LeetcodeScreen.name) {
            LeetcodeScreen()
        }
        composable(route = BottomNavScreens.HomeScreen.route) {
            HomeScreen()
        }
        composable(route = Screens.HomeScreen.name){
            TabView()
        }
        composable(route = Screens.FriendsScreen.name) {
            FriendsScreen()
        }
    }
}