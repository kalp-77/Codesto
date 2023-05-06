package com.example.codemaster.ui.screens.codechef

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Codechef
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.Repository
import com.example.codemaster.ui.screens.codeforces.GraphDataState
import com.example.codemaster.ui.theme.Black20
import com.example.codemaster.ui.theme.Codeforces10
import com.example.codemaster.ui.theme.GraphHighlight
import com.example.codemaster.ui.theme.GreenUpArrow
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LinePlot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CodechefViewModel @Inject constructor(
    val repository : Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CodechefState>(CodechefState.Loading)
    val uiState : StateFlow<CodechefState> = _uiState

    /** graph data **/
    private val _graphDataState = mutableStateOf(CodechefGraphDataState())
    val graphDataState: State<CodechefGraphDataState>
        get() = _graphDataState

    private val _starColor = mutableStateOf<Color>(Color.White)
    val starColor: State<Color>
        get() = _starColor


    init{
        viewModelScope.launch {
            repository.getCodechefUser().collect {
                if(it != null) {
                    fetchCodechefData(it)
                }
            }
        }
    }
    private fun fetchCodechefData(username : String) = viewModelScope.launch {
        try {
            val result : Flow<Response<Codechef>?> = repository.getCodechefData(username)
            result.collect {
                when(it) {
                    is Response.Loading -> {
                        _uiState.value = CodechefState.Loading
                    }
                    is Response.Success -> {
                        /** graph **/
                        val dataPoints = mutableStateListOf<DataPoint>()
                        val linePlotLines = mutableStateListOf<LinePlot.Line>()
                        var xPos = 0f
                        for(yPos in it.data?.contests!!) {
                            dataPoints.add(DataPoint(x = xPos, yPos.toFloat()))
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
                        _graphDataState.value = CodechefGraphDataState(
                            dataPoints = dataPoints,
                            linePlotLines = linePlotLines
                        )

                        /** Color **/
                        when {
                            it.data.rating < "1400" -> _starColor.value = Color(0xFF666666)
                            it.data.rating < "1600" && it.data.rating >= "1400" -> _starColor.value = Color(0xFF1E7D22)
                            it.data.rating < "1800" && it.data.rating >= "1600" -> _starColor.value = Color(0xFF3366CB)
                            it.data.rating < "2000" && it.data.rating >= "1800" -> _starColor.value = Color(0xFF684273)
                            it.data.rating < "2200" && it.data.rating >= "2000" -> _starColor.value = Color(0xFFFEBE00)
                            it.data.rating < "2500" && it.data.rating >= "2200" -> _starColor.value = Color(0xFFFE7F00)
                            else -> _starColor.value = Color.Red
                        }


                        _uiState.value = it.data.let { it1 -> CodechefState.Success(it1) }
                    }
                    is Response.Failure -> {
                        _uiState.value = CodechefState.Failure(it.message.toString())
                    }

                    else -> { _uiState.value = CodechefState.Loading }
                }
            }
        } catch (ex: Exception) {
            _uiState.value = CodechefState.Failure("Oops! something went wrong")
        }
    }
}