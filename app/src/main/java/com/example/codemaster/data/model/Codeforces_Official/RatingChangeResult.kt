package com.example.codemaster.data.model.Codeforces_Official

data class RatingChangeResult(
    val contestId: Int,
    val contestName: String,
    val handle: String,
    val newRating: Int,
    val oldRating: Int,
    val rank: Int,
    val ratingUpdateTimeSeconds: Int
)
