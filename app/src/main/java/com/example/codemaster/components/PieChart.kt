package com.example.codemaster.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.codemaster.components.graphs.DonutChart

@Composable
fun PieChart(
    progressDataList : List<Float>,
    size : Dp
) {
    DonutChart(
        modifier = Modifier
            .size(size),
        progress = progressDataList,
        colors = listOf(
            Color(0xFF00B7A2),
            Color(0xFFFEBF1E),
            Color(0xFFEE4743)
        ),
        isDonut = true,
        percentColor = Color.Black
    )
}