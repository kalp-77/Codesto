package com.example.codemaster.ui.screens.codeforces_problemset

import com.example.codemaster.data.model.codeforces_model.problemset.Problem

sealed class ProblemsetState{
    object Empty : ProblemsetState()
    object Loading : ProblemsetState()
    class Success(val data: List<Problem>) : ProblemsetState()
    class Failure(val message: String) : ProblemsetState()
}