package com.example.codemaster.ui.screens.leetcode

import com.example.codemaster.data.model.Leetcode

sealed class LeetcodeState{
    object Loading : LeetcodeState()
    class Success(val data: Leetcode) : LeetcodeState()
    class Failure(val message: String) : LeetcodeState()
}
