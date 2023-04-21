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
        startDestination = Screens.LoginScreen.route
    ) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(
                onNavigate = {
                    navController.navigate(it.route) {
                        popUpTo(it.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screens.SignupScreen.route) {
            SignupScreen(
                onNavigate = {
                    navController.navigate(it.route){
                        popUpTo(it.route){ inclusive = true }
                    }
                }
            )
        }
        composable(route = Screens.PlatformScreen.route) {
            PlatformScreen(
                onNavigate = {
                    navController.navigate(it.route) {
                        popUpTo(it.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(
                onNavigate = {
                    navController.navigate(it.route) {
                        popUpTo(it.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screens.CodechefScreen.route) {
            CodechefScreen()
        }
        composable(route = Screens.CodeforcesScreen.route) {
            CodeforcesScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable(route = Screens.ProblemsetScreen.route) {
            ProblemsetScreen()
        }
        composable(route = Screens.RatingChangeScreen.route) {
            RatingChangeScreen()
        }
        composable(route = Screens.LeetcodeScreen.route) {
            LeetcodeScreen()
        }
        composable(route = Screens.ContestsScreen.route){
            TabView()
        }
    }
}