package com.example.codemaster.ui.screens.codechef

import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LinePlot

data class CodechefGraphDataState (
    val dataPoints: List<DataPoint> = emptyList(),
    val linePlotLines: List<LinePlot.Line> = emptyList(),
)

