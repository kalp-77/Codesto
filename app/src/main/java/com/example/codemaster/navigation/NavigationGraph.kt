package com.example.codemaster.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.codemaster.ui.screens.home.HomeScreen
import com.example.codemaster.ui.screens.login.LoginScreen
import com.example.codemaster.ui.screens.platform.PlatformScreen
import com.example.codemaster.ui.screens.signup.SignupScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen.route
    ) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo(Screens.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(Screens.SignupScreen.route){
                        popUpTo(Screens.SignupScreen.route){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screens.SignupScreen.route) {
            SignupScreen(
                onNavigateToPlatform = {
                    navController.navigate(Screens.PlatformScreen.route){
                        popUpTo(Screens.PlatformScreen.route){ inclusive = true }
                    }
                }
            )
        }
        composable(route = Screens.PlatformScreen.route) {
            PlatformScreen(
                onNavigateToHome = {
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo(Screens.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screens.HomeScreen.route) {
            HomeScreen()
        }
    }
}