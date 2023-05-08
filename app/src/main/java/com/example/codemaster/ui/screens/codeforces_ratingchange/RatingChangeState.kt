package com.example.codemaster.ui.screens.codeforces_ratingchange

import com.example.codemaster.data.model.codeforces_model.RatingChangeResult

sealed class RatingChangeState{
    object Empty : RatingChangeState()

    object Loading : RatingChangeState()

    class Success(val data: List<RatingChangeResult>) : RatingChangeState()

    class Failure(val message: String) : RatingChangeState()


}
