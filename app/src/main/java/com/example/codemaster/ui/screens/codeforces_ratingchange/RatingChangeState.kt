package com.example.codemaster.ui.screens.codeforces_ratingchange

import com.example.codemaster.data.model.codeforces_model.RatingChangeResult
import com.example.codemaster.data.model.codeforces_model.UserRatingChanges

sealed class RatingChangeState{
    object Empty : RatingChangeState()

    object Loading : RatingChangeState()

    class Success(val data: List<RatingChangeResult>) : RatingChangeState()

    class Failure(val message: String) : RatingChangeState()


}
