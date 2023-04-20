package com.example.codemaster.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.codemaster.ui.screens.codechef.CodechefScreen
import com.example.codemaster.ui.screens.codeforces.CodeforcesScreen
import com.example.codemaster.ui.screens.home.HomeScreen
import com.example.codemaster.ui.screens.login.LoginScreen
import com.example.codemaster.ui.screens.login.LoginViewModel
import com.example.codemaster.ui.screens.platform.PlatformScreen
import com.example.codemaster.ui.screens.platform.PlatformViewModel
import com.example.codemaster.ui.screens.signup.SignupScreen

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
    }
}