package com.example.codemaster.navigation

sealed class Screens(val route: String) {
    object LoginScreen : Screens(route = "Login_Screen")
    object SignupScreen : Screens(route = "Signup_Screen")
    object PlatformScreen : Screens(route = "Platform_Screen")
    object HomeScreen : Screens(route = "Home_Screen")
    object CodechefScreen : Screens(route = "Codechef_Screen")
    object CodeforcesScreen : Screens(route = "Codeforces_Screen")
    object ProblemsetScreen : Screens(route = "Problemset_Screen")
    object RatingChangeScreen : Screens(route = "Rating_Change_Screen")



}