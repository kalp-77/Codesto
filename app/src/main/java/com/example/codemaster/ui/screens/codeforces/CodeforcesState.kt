package com.example.codemaster.ui.screens.codeforces

import com.example.codemaster.data.model.Codeforces
import com.example.codemaster.data.model.codeforces_model.user_info.UserInfo

sealed class CodeforcesState{
    object Loading : CodeforcesState()
    data class Success(val data: CodeforcesScreenData, val ratingStatus: Int) : CodeforcesState()
    data class Failure(val message: String) : CodeforcesState()
    data class Navigate(val screen: String): CodeforcesState()
}

data class CodeforcesScreenData(
    val userData : UserInfo,
    val graphData : Codeforces
)

data class SolvedProblemData(
    val data : Map<Int, Int>? = null
)

sealed class Screen {
    object Friends : Screen()
    object FriendsDetail : Screen()
}