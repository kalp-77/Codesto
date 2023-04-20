package com.example.codemaster.data.model

import com.google.gson.annotations.SerializedName

data class Codechef(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("contests")
    val contests: List<Int>,
    @SerializedName("country rank")
    val country_rank: Int,
    @SerializedName("div")
    val div: String,
    @SerializedName("global rank")
    val global_rank: Int,
    @SerializedName("max rating")
    val max_rating: String,
    @SerializedName("platform")
    val platform: String,
    @SerializedName("problem solved")
    val problem_solved: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("stars")
    val stars: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("username")
    val username: String
)
