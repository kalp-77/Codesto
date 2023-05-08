package com.example.codemaster.components.graphs

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codemaster.R
import com.example.codemaster.components.CustomSnackbar
import com.example.codemaster.ui.theme.Black20
import com.example.codemaster.ui.theme.Grid10
import com.example.codemaster.ui.theme.SnackbarGreen30
import com.example.codemaster.ui.theme.ThemeBlack30
import com.example.codemaster.utils.DataPointXline
import com.madrapps.plot.line.LinePlot

@SuppressLint("ResourceAsColor")
@Composable
fun LineChartCard(
    linePlotLines: List<LinePlot.Line>,
    size: Int,
    maxRating: Int
) {
    Card(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 5.dp),
        colors = CardDefaults.cardColors(ThemeBlack30),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Black20)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            )  {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.12f)
                        .size(20.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SnackbarGreen30)
                            .align(Alignment.Center),
                        painter = painterResource(id  = R.drawable.up_arrow),
                        contentDescription = null
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    androidx.compose.material3.Text(
                        modifier = Modifier,
                        fontSize = 14.sp,
                        color = Color.White,
                        text = "Rating Graph"
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Column(modifier = Modifier.padding(end = 10.dp), horizontalAlignment = Alignment.End) {
                        androidx.compose.material3.Text(
                            modifier = Modifier,
                            fontSize = 18.sp,
                            color = Color.White,
                            text = maxRating.toString(),
                            fontWeight = FontWeight.Bold
                        )
                        androidx.compose.material3.Text(
                            modifier = Modifier,
                            fontSize = 9.sp,
                            color = Color.White,
                            text = "Max Rating"
                        )
                    }
                }
            }
            LineChart(
                linePlotLines = linePlotLines,
                size = size
//                viewModel.graphDataState.value.linePlotLines,

            )
        }
    }
}



@Composable
fun LineChart(
    linePlotLines: List<LinePlot.Line>,
    size: Int
){
    val pointValues = remember { mutableStateListOf<DataPointXline>() }
    val textPaint = remember { mutableStateOf(Paint()) }
    textPaint.value.apply {
        isAntiAlias = true
        textSize = 35f
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
                        .height(30.dp)
                        .background(Black20)
                        .drawBehind {
                            this.drawIntoCanvas {
                                val width = 200f
                                val height = 60f
                                val yOffset = 20f
                                pointValues.forEach { dataPoint ->
                                    drawRoundRect(
                                        color = Grid10,
                                        topLeft = Offset(
                                            x = dataPoint.xPos - width/2,
                                            y = yOffset
                                        ),
                                        size = Size(width, height),
                                        cornerRadius = CornerRadius(x = 60f, y = 60f)
                                    )
                                    it.nativeCanvas.drawText(
                                        dataPoint.yPos
                                            .toInt()
                                            .toString(),
                                        dataPoint.xPos,
                                        yOffset + height/2 + 10f,
                                        textPaint.value
                                    )
                                }
                            }
                        }
                )
                GraphLine(
                    modifier = Modifier.height(200.dp).padding(end = 15.dp),
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
                        horizontalExtraSpace = 5.dp,
                        grid = LinePlot.Grid(
                            steps = 5,
                            color = Grid10
                        ),
                        isZoomAllowed = true,
                        xAxis = LinePlot.XAxis(
                            stepSize = 50.dp,
                            paddingBottom = 4.dp,
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

