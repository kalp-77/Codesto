package com.example.codemaster.ui.screens.codeforces

import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LinePlot

data class GraphDataState(
    val dataPoints: List<DataPoint> = emptyList(),
    val linePlotLines: List<LinePlot.Line> = emptyList(),
)