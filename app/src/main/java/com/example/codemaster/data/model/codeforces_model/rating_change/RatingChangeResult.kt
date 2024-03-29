package com.example.codemaster.data.model.codeforces_model.rating_change

import com.google.gson.annotations.SerializedName

data class RatingChangeResult(
    @SerializedName("contestId")
    val contestId: Int,
    @SerializedName("contestName")
    val contestName: String,
    @SerializedName("handle")
    val handle: String,
    @SerializedName("newRating")
    val newRating: Int,
    @SerializedName("oldRating")
    val oldRating: Int,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("ratingUpdateTimeSeconds")
    val ratingUpdateTimeSeconds: Int
)
