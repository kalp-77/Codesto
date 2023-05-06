package com.example.codemaster.components.graphs

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.codemaster.components.CustomSnackbar
import com.example.codemaster.ui.theme.Black20
import com.example.codemaster.ui.theme.Grid10
import com.example.codemaster.utils.DataPointXline
import com.madrapps.plot.line.LinePlot

@SuppressLint("ResourceAsColor")
@Composable
fun LineChart(
    linePlotLines: List<LinePlot.Line>,
    size: Int
){
    val pointValues = remember { mutableStateListOf<DataPointXline>() }
    val textPaint = remember { mutableStateOf(Paint()) }
    textPaint.value.apply {
        isAntiAlias = true
        textSize = 75f
        color = android.graphics.Color.WHITE
        textAlign = Paint.Align.CENTER
    }
    if(linePlotLines.isNotEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(Black20)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(45.dp)
                        .background(Black20)
                        .drawBehind {
                            this.drawIntoCanvas {
                                val width = 200f
                                val height = 90f
                                val yOffset = 40f
                                pointValues.forEach { dataPoint ->
//                                    drawRoundRect(
//                                        color = Color.Black,
//                                        topLeft = Offset(
//                                            x = 100f,
//                                            y = yOffset
//                                        ),
//                                        size = Size(width, height),
//                                        cornerRadius = CornerRadius(x = 10f, y = 10f)
//                                    )

                                    it.nativeCanvas.drawText(
                                        dataPoint.yPos
                                            .toInt()
                                            .toString(),
                                        160f,
                                        yOffset + height / 2,
                                        textPaint.value
                                    )
                                }
                            }
                        }
                )
                GraphLine(
                    modifier = Modifier.height(250.dp),
                    contentSize = size,
                    plot = LinePlot(
                        lines = linePlotLines,
                        selection = LinePlot.Selection(
                            enabled = true,
                            highlight = LinePlot.Connection(
                                color = Color.LightGray,
                                strokeWidth = 1.dp,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f)),
                            ),
                        ),
                        horizontalExtraSpace = 10.dp,
                        grid = LinePlot.Grid(
                            steps = 6,
                            color = Grid10
                        ),
                        isZoomAllowed = true,
                        xAxis = LinePlot.XAxis(
                            stepSize = 50.dp,
                            steps = 6,
                            content = { min, offset, max ->
                                for (it in 0..0) {
                                    val value = it * offset + min
                                    Text(
                                        text = "",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.caption,
                                        color = MaterialTheme.colors.onSurface
                                    )
                                    if (value > max) {
                                        break
                                    }
                                }

                            }
                        ),
                        yAxis = LinePlot.YAxis(
                            steps = 5,
                            roundToInt = true,
                            content = {  min, offset, _ ->
                                for (it in 0 until 5) {
                                    val yValue = it * offset + min
                                    Text(
                                        text = yValue.toInt().toString(),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.caption,
                                        color = Grid10
                                    )
                                }
                            }
                        )
                    ),
                    onSelectionStart = {},
                    onSelectionEnd = { pointValues.clear() },
                    onSelection = { xLine, points ->
                        pointValues.clear()
                        points.forEach {
                            pointValues.add(DataPointXline(xPos = xLine, yPos = it.y, value = it.y))
                        }
                    }
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    CustomSnackbar()
                }
            }
        }
    }
}

