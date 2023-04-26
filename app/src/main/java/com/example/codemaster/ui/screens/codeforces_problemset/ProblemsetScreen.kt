package com.example.codemaster.ui.screens.codeforces_problemset

import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.MyApplication
import com.example.codemaster.WebViewActivity
import com.example.codemaster.data.model.codeforces_model.Problem

val font = FontFamily.SansSerif
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProblemsetScreen(
    viewModel: ProblemsetViewModel = hiltViewModel()
){
    val state = viewModel.uiState.collectAsState().value
    val showDialog = remember { mutableStateOf(false) }
    val expanded = remember { mutableStateOf(false) }
    val tagList = ProblemsetUtils().tags()
    val selectedTagList = ArrayList<String>()

    Column (
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Problem Set",
                    modifier = Modifier
                        .padding(start = 20.dp, top = 15.dp, bottom = 10.dp),
                    textAlign = TextAlign.Start,
                    fontFamily = font,
                    fontSize = 20.sp,
                    color = Color(0xFF2A265C)
                )
            }
            Column(
                modifier = Modifier.padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Info, contentDescription = "null",
                        modifier = Modifier
                            .padding(end = 30.dp)
                            .align(Alignment.CenterVertically)
                            .clickable(
                                onClick = {
                                    expanded.value = true
                                }
                            ),
                        tint = Color(0xFF2A265C),
                    )
                    Icon(
                        imageVector = Icons.Default.DateRange, contentDescription = "null",
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .align(Alignment.CenterVertically)
                            .clickable(
                                onClick = {
                                    showDialog.value = true
                                }
                            ),
                        tint = Color(0xFF2A265C),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(5.dp))
        when(state){
            is ProblemsetState.Empty -> {
            }
            is ProblemsetState.Loading ->{
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ProblemsetState.Failure -> {
            //ErrorDialog("dsncsdn")
                Toast.makeText(LocalContext.current, "error", Toast.LENGTH_SHORT).show()
            }
            is ProblemsetState.Success -> {
                Problems(
                    data = state.data,
                    tagList = tagList,
                    showDialog = showDialog,
                    expanded = expanded,
                    selectedTagList = selectedTagList, //showWebView,
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Problems(
    data: List<Problem>,
    tagList: ArrayList<String>,
    showDialog : MutableState<Boolean>,
    expanded : MutableState<Boolean>,
    selectedTagList: ArrayList<String>
) {
//    if(showWebView.value){
//        WebViewPager(
//            url = "https://codeforces.com/problemset/problem/${data[0].contestId}/${data[0].index}"
//        )
//    }
    if (showDialog.value) {
        TagDialog(tagList, selectedTagList, showDialog)
    }
    if (expanded.value) {
        RatingMenuList(expanded)
    }
    if(data.isEmpty()){
        Nul("No result found!")
    }
    Column{
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
        ) {
            items(data) {
                ProblemCard(
                    contestId = it.contestId.toString(),
                    index = it.index,
                    contestName = it.name,
                    contestRating = it.rating.toString()
                )
            }
        }
    }
}

@Composable
fun Nul(msg : String){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = msg,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            fontFamily = font
        )
    }
}
@Composable
fun ProblemCard(
    contestId : String,
    index: String,
    contestName : String,
    contestRating : String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xE9ECEAF8))
    ) {
        Divider(
            modifier = Modifier.fillMaxSize(1f),
            color = Color(0xFFF3F3F3),
            thickness = 2.dp
        )
        Column(
            modifier = Modifier
                .background(Color(0xFFFFFFFF))
                .padding(start = 10.dp, top = 20.dp, bottom = 20.dp, end = 10.dp)
        ){
            val url = "https://codeforces.com/problemset/problem/${contestId}/${index}"
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        onClick = {
                            val myIntent = Intent(MyApplication.instance, WebViewActivity::class.java)
                            myIntent.putExtra("key", url)
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            MyApplication.instance.startActivity(myIntent)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Column(
                    modifier = Modifier
                        .width(70.dp)
                ) {
                    Text(
                        text = "${contestId}${index}",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier,
                        fontFamily = font,
                        color = Color(0xFF2A265C)
                    )
                }
                Column(
                    modifier = Modifier
                        .width(200.dp)
                ) {
                    Text(
                        text = contestName,
                        modifier = Modifier,
                        textDecoration = TextDecoration.Underline,
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2A265C)
                    )
                }
                Column(
                    modifier = Modifier
                        .width(50.dp),
                    horizontalAlignment = Alignment.End
                ){
                    Text(
                        text= contestRating,
                        textAlign = TextAlign.End,
                        fontFamily = font,
                        color = Color(0xFF2A265C)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RatingMenuList(
    expanded: MutableState<Boolean>,
    viewModel: ProblemsetViewModel = hiltViewModel(),
) {
    val problemRatings = ProblemsetUtils().problemRatings
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        offset = DpOffset(380.dp,30.dp)
    ) {
        for(rating in problemRatings){
            DropdownMenuItem(
                text = {
                    Text(rating.toString())
                },
                onClick = {
                    viewModel.filterData(rating)
                    expanded.value = false
                },
            )
        }
    }
}
@Composable
fun TagDialog(
    tagList: ArrayList<String>,
    selectedTagList: ArrayList<String>,
    state: MutableState<Boolean>,
    viewModel: ProblemsetViewModel = hiltViewModel()
){
    AlertDialog(
        onDismissRequest = {state.value = false},
        modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 16.dp),
        text = {
            val checkStates = tagList.map { remember { mutableStateOf(false) } }
            Column{
                Column(
                    Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Select tag",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(0.dp,10.dp,0.dp,10.dp),
                        fontFamily = font
                    )
                    Divider(modifier = Modifier.padding(bottom = 10.dp))
                }
                Spacer(modifier = Modifier.height(5.dp))
                LazyColumn(
                    Modifier.weight(1f), contentPadding = PaddingValues(4.dp,4.dp)
                ) {
                    itemsIndexed(tagList) { index, text ->
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = checkStates[index].value,
                                onCheckedChange = {
                                    checkStates[index].value = !checkStates[index].value
                                    if(checkStates[index].value) {
                                        selectedTagList.add(text)
                                    }
                                    else if(!checkStates[index].value) {
                                        selectedTagList.remove(text)
                                    }
                                },
                                colors = CheckboxDefaults.colors(checkedColor = Color.Black)
                            )
                            Text(
                                text = text,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(start = 8.dp),
                                fontFamily = font
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Column{
                Button(
                    onClick = {
                        state.value = false
                        viewModel.fetchProblems(selectedTagList)
                        selectedTagList.clear()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                ) {
                    Text(
                        "OK",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    )
}

