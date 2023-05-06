package com.example.codemaster.data.model

import androidx.compose.ui.graphics.painter.Painter

data class ContestItem(
    val duration: String,
    val end_time: String,
    val in_24_hours: String,
    val name: String,
    val site: String,
    val start_time: String,
    val status: String,
    val url: String,
    var icon: Int
)
