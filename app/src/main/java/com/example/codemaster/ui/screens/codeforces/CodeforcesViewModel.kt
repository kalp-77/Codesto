package com.example.codemaster.ui.screens.codeforces

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.DefaultStrokeLineMiter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.Repository
import com.example.codemaster.ui.theme.Black20
import com.example.codemaster.ui.theme.BlueG
import com.example.codemaster.ui.theme.Codeforces10
import com.example.codemaster.ui.theme.GraphHighlight
import com.example.codemaster.ui.theme.Green10
import com.example.codemaster.ui.theme.GreenUpArrow
import com.example.codemaster.ui.theme.LineColor
import com.example.codemaster.utils.NavigateUI
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CodeforcesViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CodeforcesState>(CodeforcesState.Loading)
    val uiState : StateFlow<CodeforcesState> = _uiState.asStateFlow()

    /** graph data **/
    private val _graphDataState = mutableStateOf(GraphDataState())
    val graphDataState: State<GraphDataState>
        get() = _graphDataState

    private val _solvedProblemState = MutableStateFlow<Response<SolvedProblemData>?>(null)
    val solvedProblemState = _solvedProblemState.asStateFlow()


    private val _totalProblemSolved = mutableStateOf(0)
    val totalProblemSolved: State<Int>
        get() = _totalProblemSolved


    // handle events send by the ui layer
    private val _uiEvents = Channel<NavigateUI>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init{
        viewModelScope.launch {
            repository.getCodeforcesUser().collect {
                if(it != null) {
                    fetchCodeforcesData(it.toString())
                    fetchSolvedProblemData(it.toString())
                }
            }
        }

    }
    private fun fetchCodeforcesData(username : String) = viewModelScope.launch {
        try{
            val data = repository.getCodeforcesScreenData(username)  // data = codeforcesScreenData : (userInfo + graphData)
            data.collect {
                when(it) {
                    is Response.Loading -> {
                        _uiState.value = CodeforcesState.Loading
                    }
                    is Response.Success -> {
                        val dataPoints = mutableStateListOf<DataPoint>()
                        val linePlotLines = mutableStateListOf<LinePlot.Line>()
                        val ratingStatus = it.data!!.graphData.contest.last() - (it.data.graphData.contest.elementAt(it.data.graphData.contest.size - 2))

                        var xPos = 0f
                        for(yPos in it.data?.graphData?.contest?.reversed()!!) {
                            dataPoints.add(
                                DataPoint(
                                    x = xPos,
                                    y = yPos.toFloat()
                                )
                            )
                            xPos += 1f
                        }
                        linePlotLines.add(
                            LinePlot.Line(
                                dataPoints = dataPoints,
                                LinePlot.Connection(
                                    color = GreenUpArrow,
                                    strokeWidth = 1.dp,
                                ),
                                LinePlot.Intersection(
                                    draw = {offset, dataPoint ->
                                        drawCircle(
                                            color = GreenUpArrow,
                                            radius = 8f,
                                            center = offset
                                        )
                                        drawCircle(
                                            color = Black20,
                                            radius = 6f,
                                            center = offset
                                        )
                                    }

                                ),
                                LinePlot.Highlight(
                                    draw = {offset ->
                                        drawCircle(
                                            color = GraphHighlight,
                                            radius = 12f,
                                            center = offset
                                        )
                                        drawCircle(
                                            color = GreenUpArrow,
                                            radius = 8f,
                                            center = offset
                                        )
                                        drawCircle(
                                            color = Black20,
                                            radius = 6f,
                                            center = offset
                                        )
                                    }
                                )
                            )
                        )
                        _graphDataState.value = GraphDataState(
                            dataPoints = dataPoints,
                            linePlotLines = linePlotLines
                        )
                        _uiState.value = it.data.let { it1 -> CodeforcesState.Success(data = it1, ratingStatus = ratingStatus) }
                    }
                    is Response.Failure -> {
                        _uiState.value = CodeforcesState.Failure(it.message.toString())
                    }
                    else -> {}
                }
            }
        }
        catch (e:Exception) {
            _uiState.value = CodeforcesState.Failure("Oops! Something went wrong")
        }
    }

    private fun fetchSolvedProblemData(username: String) = viewModelScope.launch {
        try {
            val data = repository.getSolvedProblemData(username = username)  // data = codeforcesScreenData : (userInfo + graphData)
            data.collect { it ->
                when(it) {
                    is Response.Loading -> {
                        _solvedProblemState.value = Response.Loading()
                    }
                    is Response.Success -> {
                        val rawData = it.data?.result?.filter {
                            it.verdict == "OK"
                        }
                        val actualData = rawData?.groupBy {
                            it.problem.rating
                        }
                        val groupedData = mutableMapOf<Int,Int>()
                        var totalCount = 0
                        actualData?.map { res ->
                            if(res.key >=800) {
                                totalCount += res.value.size
                                groupedData[res.key] = res.value.size
                            }
                        }
                        _totalProblemSolved.value = totalCount
                        _solvedProblemState.value = Response.Success(data = SolvedProblemData(data = groupedData))
                    }
                    is Response.Failure -> {
                        _solvedProblemState.value = Response.Failure(it.message.toString())
                    }
                    else -> {}
                }
            }

        }
        catch (e : Exception) {
            _solvedProblemState.value = Response.Failure("no data")
        }
    }

    fun onEvent(event : NavigateUI) = viewModelScope.launch {
        _uiEvents.send(event)
    }




}