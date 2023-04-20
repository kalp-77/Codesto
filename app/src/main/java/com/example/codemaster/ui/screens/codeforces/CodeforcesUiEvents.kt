package com.example.codemaster.ui.screens.codeforces

sealed class CodeforcesUiEvents{
    object onProblemsClick : CodeforcesUiEvents()
    object onRatingClick : CodeforcesUiEvents()
}
