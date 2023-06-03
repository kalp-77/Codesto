package com.example.codemaster.ui.screens.platform

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.R
import com.example.codemaster.components.SaveUsernameCard
import com.example.codemaster.data.model.Response
import com.example.codemaster.navigation.Screens
import com.example.codemaster.ui.theme.Comrades100
import com.example.codemaster.ui.theme.Platform100
import com.example.codemaster.utils.NavigateUI
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState")
@Composable
fun PlatformScreen(
    onNavigate: (route: Screens)-> Unit,
    viewModel: PlatformViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect {
            when (it) {
                is NavigateUI.Navigate -> {
                    onNavigate(it.onNavigate)
                }

                is NavigateUI.Snackbar -> {

                }

                is NavigateUI.PopBackStack -> {

                }
            }
        }
    }


    val scope = rememberCoroutineScope()
    // Observe the response state using StateFlow
    val platformState by viewModel.platformState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = platformState) {
        when (platformState) {
            is Response.Success -> {
                viewModel.onEvent(
                    NavigateUI.Navigate(onNavigate = Screens.HomeScreen)
                )
                Toast.makeText(context, "${platformState.data}", Toast.LENGTH_SHORT).show()
            }

            is Response.Failure -> {
                Toast.makeText(context, "${platformState.message}", Toast.LENGTH_SHORT).show()
            }

            is Response.Loading -> {

            }
        }
    }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(Color.Black)
            .verticalScroll(state = rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(55.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
            ) {

            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "Kalp Patel",
                    fontSize = 24.sp,
                    color = Color.White
                )
                Text(
                    text = "kalp07patel@gmail.com",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "Add Username",
            fontSize = 16.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(Comrades100),
            shape = RoundedCornerShape(5.dp)

        ) {
            Box(
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 30.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "USERNAME",
                        fontSize = 10.sp,
                        color = Color.LightGray
                    )
                    BasicTextField(
                        value = viewModel.username.value,
                        onValueChange = {
                            viewModel.username.value = it
                        },
                        textStyle = TextStyle(fontSize = 12.sp, color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 3.dp),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        SaveUsernameCard(textValue = viewModel.codeforcesUser, platform = "codeforces")
        Spacer(modifier = Modifier.height(5.dp))
        SaveUsernameCard(textValue = viewModel.codechefUser, platform = "codechef")
        Spacer(modifier = Modifier.height(5.dp))
        SaveUsernameCard(textValue = viewModel.leetcodeUser, platform = "leetcode")
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .height(75.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(Platform100),
            shape = RoundedCornerShape(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(75.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, end = 10.dp, start = 30.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Platform100)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.coding_platform),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "Filter coding platforms",
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(Modifier, verticalAlignment = Alignment.CenterVertically) {
                                Canvas(
                                    modifier = Modifier
                                        .padding(end = 6.dp)
                                        .size(5.dp)
                                ) {
                                    drawCircle(Color.Black)
                                }
                                Text(
                                    text = "8 platforms selected",
                                    fontSize = 10.sp,
                                    color = Color.DarkGray
                                )
                            }
                        }
                        Box(
                            modifier = Modifier.size(40.dp),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Image(
                                painter = rememberVectorPainter(image = Icons.Default.ArrowForward),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(18.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(35.dp))
        Button(
            onClick = {
                scope.launch {
                    viewModel.saveUsernames(
                        cfUsername = viewModel.codeforcesUser.value,
                        ccUsername = viewModel.codechefUser.value,
                        lcUsername = viewModel.leetcodeUser.value,
                        username = viewModel.username.value
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.White),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Update",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}





//    LaunchedEffect(key1 = platformState?.isSuccess) {
//        if (platformState?.isSuccess?.isNotEmpty() == true) {
//            val success = platformState?.isSuccess
//            Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
//        }
//    }
//    LaunchedEffect(key1 = platformState?.isFailure) {
//        if (platformState?.isFailure?.isNotBlank() == true) {
//            val error = platformState?.isFailure
//            Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
//        }
//    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(start = 30.dp, end = 30.dp),
//        verticalArrangement = Arrangement.Center
//    ) {
//
//        TextBox(
//            platform = "codechef",
//            scope = scope,
//            username = codechefState
//        )
//        TextBox(
//            platform = "codeforces",
//            scope = scope,
//            username = codeforcesState
//        )
//        TextBox(
//            platform = "leetcode",
//            scope = scope,
//            username = leetcodeState
//        )
//        Spacer(modifier = Modifier.height(20.dp))
//        Button(
//            onClick = {
//                viewModel.onEvent(NavigateUI.Navigate(Screens.HomeScreen))
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 20.dp, start = 3.dp, end = 3.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Black,
//                contentColor = Color.White,
//            ),
//            shape = RoundedCornerShape(15.dp)
//        ) {
//            Text(
//                text = "Done",
//                color = Color.White,
//                modifier = Modifier
//                    .padding(7.dp),
//                fontSize = 16.sp
//            )
//        }
//    }

