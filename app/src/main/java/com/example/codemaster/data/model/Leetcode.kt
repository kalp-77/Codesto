package com.example.codemaster.data.model

import com.google.gson.annotations.SerializedName

data class Leetcode(
    @SerializedName("easy_questions_solved")
    val easy_questions_solved: String,
    @SerializedName("hard_questions_solved")
    val hard_questions_solved: String,
    @SerializedName("medium_questions_solved")
    val medium_questions_solved: String,
    @SerializedName("ranking")
    val ranking: String,
    @SerializedName("total_easy_questions")
    val total_easy_questions: String,
    @SerializedName("total_hard_questions")
    val total_hard_questions: String,
    @SerializedName("total_medium_questions")
    val total_medium_questions: String,
    @SerializedName("total_problems_solved")
    val total_problems_solved: String,
    @SerializedName("username")
    val username: String
)
