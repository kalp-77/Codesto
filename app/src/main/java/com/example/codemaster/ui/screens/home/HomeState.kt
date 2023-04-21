package com.example.codemaster.ui.screens.home

import com.example.codemaster.data.model.Contest

sealed class HomeState{
    object Loading : HomeState()
    class Success(val data: Contest) : HomeState()
    class Failure(val message: String) : HomeState()
}
