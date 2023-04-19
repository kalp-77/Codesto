package com.example.codemaster.navigation

sealed class Screens(val route: String) {
    object LoginScreen : Screens(route = "Login_Screen")
    object SignupScreen : Screens(route = "Signup_Screen")
    object PlatformScreen : Screens(route = "Platform_Screen")
    object HomeScreen : Screens(route = "Home_Screen")

}