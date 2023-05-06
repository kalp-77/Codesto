package com.example.codemaster.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.codemaster.R
import androidx.compose.ui.res.vectorResource


enum class Screens{
    LoginScreen,
    SignupScreen,
    PlatformScreen,
    HomeScreen,
    CodechefScreen,
    CodeforcesScreen,
    LeetcodeScreen,
    ProblemsetScreen,
    RatingChangeScreen,
}
sealed class BottomNavScreens(
    val route: String,
    val icon: Int,
    val title: String
) {
    object HomeScreen : BottomNavScreens(
        Screens.HomeScreen.name,
        R.drawable.icons_contests,
        "Contests"
    )

    object CodechefScreen : BottomNavScreens(
        Screens.CodechefScreen.name,
        R.drawable.icons_codechef,
        "Codechef"
    )

    object CodeforcesScreen : BottomNavScreens(
        Screens.CodeforcesScreen.name,
        R.drawable.icons_codeforces,
        "Codeforces"
    )

    object LeetcodeScreen : BottomNavScreens(
        Screens.LeetcodeScreen.name,
        R.drawable.icons_leetcode,
        "Leetcode"
    )
}