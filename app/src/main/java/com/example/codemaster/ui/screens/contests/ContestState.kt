package com.example.codemaster.ui.screens.contests

import com.example.codemaster.data.model.Contest

sealed class ContestState{
    object Loading : ContestState()
    class Success(val data: Contest) : ContestState()
    class Failure(val message: String) : ContestState()
}
