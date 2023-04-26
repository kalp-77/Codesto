package com.example.codemaster.ui.screens.home

import com.example.codemaster.data.model.Contest
import com.example.codemaster.data.model.ContestItem

sealed class HomeState{
    object Loading : HomeState()
    class Success(val data: List<ContestItem>) : HomeState()
    class Failure(val message: String) : HomeState()
}
