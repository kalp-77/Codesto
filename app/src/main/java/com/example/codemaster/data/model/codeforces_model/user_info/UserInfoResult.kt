package com.example.codemaster.data.model.codeforces_model.user_info

import com.google.gson.annotations.SerializedName

data class UserInfoResult(
    @SerializedName("avatar")
    val avatar: String? = "",
    @SerializedName("city")
    val city: String? = "",
    @SerializedName("contribution")
    val contribution: Int? = 0,
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("firstName")
    val firstName: String? = "",
    @SerializedName("friendOfCount")
    val friendOfCount: Int? =0,
    @SerializedName("handle")
    val handle: String? = "",
    @SerializedName("lastName")
    val lastName: String? = "",
    @SerializedName("lastOnlineTimeSeconds")
    val lastOnlineTimeSeconds: Int? = 0,
    @SerializedName("maxRank")
    val maxRank: String? = "",
    @SerializedName("maxRating")
    val maxRating: Int? = 0,
    @SerializedName("organization")
    val organization: String? = "",
    @SerializedName("rank")
    val rank: String? = "",
    @SerializedName("rating")
    val rating: Int? = 0,
    @SerializedName("registrationTimeSeconds")
    val registrationTimeSeconds: Int = 0,
    @SerializedName("titlePhoto")
    val titlePhoto: String = ""
)
