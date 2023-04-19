package com.example.codemaster.ui.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.codemaster.ui.screens.login.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    Button(
        onClick = {
            scope.launch {
                viewModel.logout()
            }
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
            text = "Log in",
            color = Color.White,
            modifier = Modifier
                .padding(7.dp),
            fontSize = 16.sp
        )
    }
}