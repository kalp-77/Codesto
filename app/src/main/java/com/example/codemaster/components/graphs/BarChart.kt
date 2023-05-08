package com.example.codemaster.components.graphs

import android.graphics.Typeface
import android.text.TextUtils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codemaster.ui.theme.Darkblack30
import com.example.codemaster.ui.theme.Grid10
import com.example.codemaster.ui.theme.ThemeLavender
import com.example.codemaster.utils.rememberChartStyle
import com.example.codemaster.utils.rememberMarker
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry

@Composable
fun BarChart(
    barGraphData : Map<Int,Int>?,
    totalProblemSolved : Int
) {

    val groupedData = barGraphData
    val solvedData = mutableListOf<FloatEntry>()
    var total = 0
    groupedData?.forEach {
        total += it.value
        solvedData.add(
            FloatEntry(
                x = it.key.toFloat(),
                y = it.value.toFloat()
            )
        )
    }
    val chartEntryModelProducer = ChartEntryModelProducer(solvedData)
    Card(
        colors = CardDefaults.cardColors(Darkblack30),
        modifier = Modifier
            .padding(10.dp)
            .border(
                BorderStroke(width = 1.dp, color = Grid10),
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .wrapContentHeight(),
                Arrangement.Start,
                Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .padding(start = 15.dp)
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(ThemeLavender)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, top = 5.dp)
                ) {
                    Text(
                        text = "Total problem solved",
                        fontSize = 10.sp,
                        color = Color.LightGray
                    )
                    Text(
                        text = totalProblemSolved.toString(),
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(5.dp)
            ) {

                textComponent {
                    color = android.graphics.Color.BLACK
                    textSizeSp = 12f
                    typeface = Typeface.MONOSPACE
                    ellipsize = TextUtils.TruncateAt.END
                }
                ProvideChartStyle(rememberChartStyle(chartColors)) {
                    val defaultColumns =
                        currentChartStyle.columnChart.columns
                    Chart(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 6.dp,
                                end = 15.dp,
                                top = 6.dp,
                            )
                            .background(Darkblack30)
                            .height(200.dp),
                        chart = columnChart(
                            columns = remember(defaultColumns) {
                                defaultColumns.map { defaultColumn ->
                                    LineComponent(
                                        defaultColumn.color,
                                        COLUMN_WIDTH_DP,
                                        defaultColumn.shape
                                    )
                                }
                            },
                            spacing = 20.dp,

                            ),
                        model = chartEntryModelProducer.getModel(),
                        bottomAxis = bottomAxis(),
                        isZoomEnabled = true,
                        marker = rememberMarker()
                    )
                }
            }
        }
    }
}
private const val COLUMN_WIDTH_DP = 12f
private val chartColors = listOf(Color(0xff848A99))
