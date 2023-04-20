package com.example.codemaster.ui.screens.codechef

import com.example.codemaster.data.model.Codechef

sealed class CodechefState {
    object Loading : CodechefState()
    class Success(val data: Codechef) : CodechefState()
    class Failure(val message: String) : CodechefState()
}