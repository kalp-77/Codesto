package com.example.codemaster.ui.screens.platform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.components.TextBox
import com.example.codemaster.navigation.Screens
import com.example.codemaster.utils.NavigateUI


@Composable
fun PlatformScreen(
    onNavigate: (route: Screens)-> Unit,
    viewModel: PlatformViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect {
            when(it) {
                is NavigateUI.Navigate -> {
                    onNavigate(it.onNavigate)
                }
                is NavigateUI.Snackbar -> {

                }
                is NavigateUI.PopBackStack-> {

                }
            }
        }
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val codechefState by viewModel.codechefUser.collectAsState()
    val codeforcesState by viewModel.codeforcesUser.collectAsState()
    val leetcodeState by viewModel.leetcodeUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center
    ) {

        TextBox(
            platform = "codechef",
            scope = scope,
            username = codechefState
        )
        TextBox(
            platform = "codeforces",
            scope = scope,
            username = codeforcesState
        )
        TextBox(
            platform = "leetcode",
            scope = scope,
            username = leetcodeState
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                viewModel.onEvent(NavigateUI.Navigate(Screens.HomeScreen))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 3.dp, end = 3.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "Done",
                color = Color.White,
                modifier = Modifier
                    .padding(7.dp),
                fontSize = 16.sp
            )
        }
    }
}

/*
@Preview
@Composable
fun CodemasterPreview() {
    CodeMasterTheme {
        PlatformScreen()
    }
}
*/