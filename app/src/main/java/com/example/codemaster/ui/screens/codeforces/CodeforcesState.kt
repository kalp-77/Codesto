package com.example.codemaster.ui.screens.codeforces

import com.example.codemaster.data.model.Codeforces
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.model.codeforces_model.UserInfo
import com.example.codemaster.data.model.codeforces_model.UserRatingChanges

sealed class CodeforcesState{
    object Loading : CodeforcesState()
    data class Success(val data: CodeforcesScreenData) : CodeforcesState()
    data class Failure(val message: String) : CodeforcesState()
    data class Navigate(val screen: String): CodeforcesState()
}

data class CodeforcesScreenData(
    val userData : UserInfo,
    val ratingChange : UserRatingChanges
)
