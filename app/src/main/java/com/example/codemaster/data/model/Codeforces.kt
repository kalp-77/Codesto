package com.example.codemaster.data.model

import com.google.gson.annotations.SerializedName

data class Codeforces(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("contest")
    val contest: List<Int>,
    @SerializedName("max rank")
    val maxRank: String,
    @SerializedName("max rating")
    val maxRating: Int,
    @SerializedName("platform")
    val platform: String,
    @SerializedName("problem solved")
    val problemSolved: String,
    @SerializedName("rank")
    val rank: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("username")
    val username: String
)
