package com.example.codemaster.data.model.codeforces_model.problem_solved

data class Problem(
    val contestId: Int,
    val index: String,
    val name: String,
    val points: Double,
    val rating: Int,
    val tags: List<String>,
    val type: String
)