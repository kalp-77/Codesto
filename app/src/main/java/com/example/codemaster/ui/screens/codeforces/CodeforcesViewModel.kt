package com.example.codemaster.ui.screens.codeforces

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.model.codeforces_model.user_info.UserInfoResult
import com.example.codemaster.data.source.repository.Repository
import com.example.codemaster.ui.theme.Black20
import com.example.codemaster.ui.theme.Graph
import com.example.codemaster.ui.theme.GraphHighlight
import com.example.codemaster.ui.theme.GreenUpArrow
import com.example.codemaster.ui.theme.ProfileBackground100
import com.example.codemaster.utils.NavigateUI
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LinePlot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CodeforcesViewModel @Inject constructor(
    val repository: Repository,
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


    private var _friendGet = MutableStateFlow<Response<UserInfoResult>?>(null)
    val friendGet : StateFlow<Response<UserInfoResult>?> = _friendGet.asStateFlow()

    private var _friendSave = MutableStateFlow<Response<String>?>(null)
    val friendSave : StateFlow<Response<String>?> = _friendSave.asStateFlow()

    private val _friendList = MutableStateFlow<List<UserInfoResult>?>(emptyList())
    val friendList: StateFlow<List<UserInfoResult>?> = _friendList.asStateFlow()



    // handle events send by the ui layer
    private val _uiEvents = Channel<NavigateUI>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init{
        viewModelScope.launch {
            repository.getCodeforcesUser().collect {
                if(it != null) {
                    fetchCodeforcesData(it.toString())
                    fetchSolvedProblemData(it.toString())
                    getAllFriends()
                }
            }
        }
    }

    fun fetchCodeforcesData(username : String) = viewModelScope.launch {
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
                        for(yPos in it.data.graphData.contest.reversed()) {
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
                                    color = ProfileBackground100,
                                    strokeWidth = 1.dp,
                                ),
                                LinePlot.Intersection(
                                    draw = {offset, dataPoint ->
                                        drawCircle(
                                            color = ProfileBackground100,
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
            _uiState.value = CodeforcesState.Failure(e.message.toString())
        }
    }

    fun getFriends(username: String) = viewModelScope.launch {
        val data = repository.getCodeforcesScreenData(username)
        data.collect {
            try {
                when(it) {
                    is Response.Loading -> {
                        _friendGet.value = Response.Loading()
                    }
                    is Response.Success -> {
                        _friendGet.value = Response.Success(data = it.data?.userData?.result?.get(0))
                    }
                    is Response.Failure -> {
                        _friendGet.value = Response.Failure(message = it.message.toString())
                    }
                    else -> {}
                }
            } catch (e : Exception) {
                _friendGet.value = Response.Failure(message = e.message.toString())

            }
        }
    }
    fun saveFriends(user: UserInfoResult) = viewModelScope.launch {
        try {
            val saveFriendData = repository.saveFriends(friend = user)
            saveFriendData.collect { friendData ->
                when (friendData) {
                    is Response.Loading -> {

                        _friendSave.value = Response.Loading()
                    }
                    is Response.Success -> {
                        val currentList = _friendList.value
                        val updatedList = currentList?.toMutableList()
                        updatedList?.add(user)
                        _friendList.value = updatedList
                        _friendSave.value = Response.Success(data = "Friend Added")

                    }
                    is Response.Failure -> {
                        _friendSave.value = Response.Failure(message = friendData.message.toString())
                    }
                }
            }
        } catch (e: Exception) {
            _friendSave.value = Response.Failure(e.message.toString())
        }
    }

    private fun getAllFriends() = viewModelScope.launch {
        try {
            val data = repository.getAllFriends()
            data.collect{
                when(it) {
                    is Response.Loading -> {

                    }
                    is Response.Success -> {
                        _friendList.value = it.data
                    }
                    is Response.Failure-> {

                    }
                    else -> {}
                }
            }
        } catch (e:Exception) {
            Log.d("kalp", "expection getAllFriends: ${e.message.toString()}")
        }
    }

    fun deleteFriend(friend: UserInfoResult) = viewModelScope.launch {
        try {
            repository.deleteFriend(friend = friend)
            val currentList = _friendList.value
            val updatedList = currentList?.toMutableList()
            updatedList?.remove(friend)
            _friendList.value = updatedList

        } catch (e:Exception) {
            Log.d("kalp", "deleteFriend: ${e.message.toString()}")
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