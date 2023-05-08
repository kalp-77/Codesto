package com.example.codemaster.data.model.codeforces_model.problem_solved

data class Author(
    val contestId: Int,
    val ghost: Boolean,
    val members: List<Member>,
    val participantType: String,
    val room: Int,
    val startTimeSeconds: Int
)