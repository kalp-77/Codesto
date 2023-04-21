package com.example.codemaster.navigation

import com.example.codemaster.R

sealed class Screens(val route: String) {
    object LoginScreen : Screens(route = "Login_Screen")
    object SignupScreen : Screens(route = "Signup_Screen")
    object PlatformScreen : Screens(route = "Platform_Screen")
    object HomeScreen : Screens(route = "Home_Screen")
    object CodechefScreen : Screens(route = "Codechef_Screen")
    object CodeforcesScreen : Screens(route = "Codeforces_Screen")
    object LeetcodeScreen : Screens(route = "Leetcode_Screen")
    object ProblemsetScreen : Screens(route = "Problemset_Screen")
    object RatingChangeScreen : Screens(route = "Rating_Change_Screen")
    object ContestsScreen : Screens(route = "Contests_Screen")

}

sealed class BottomNavScreens(
    val icon: Int,
    val route: String
){
    object ContestsScreen : BottomNavScreens(icon = R.drawable.icons_contests, route = "Contests_Screen")
    object CodechefScreen : BottomNavScreens(icon = R.drawable.icons_codechef, route = "Contests_Screen")
    object CodeforcesScreen : BottomNavScreens(icon = R.drawable.icons_codeforces, route = "Contests_Screen")
    object LeetcodeScreen : BottomNavScreens(icon = R.drawable.icons_leetcode, route = "Contests_Screen")


}