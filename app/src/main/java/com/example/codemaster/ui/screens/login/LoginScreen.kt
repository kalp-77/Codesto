package com.example.codemaster.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.data.model.Response
import com.example.codemaster.navigation.Screens
import com.example.codemaster.utils.NavigateUI
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigate: (route: Screens)-> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var email by rememberSaveable { mutableStateOf("")}
    var password by rememberSaveable { mutableStateOf("")}
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val userDetailState = viewModel.userDetails.collectAsState()

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
    userDetailState.value.let {
        when(it) {
            is Response.Loading -> {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Response.Success -> {
                val userData = userDetailState.value?.data
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show()
                }
            }
            is Response.Failure -> {
                LaunchedEffect(Unit) {
                    val failure = userDetailState.value?.message
                    Toast.makeText(context, "$failure", Toast.LENGTH_LONG).show()
                }
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LogIn",
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            color = Color.Gray,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(modifier = Modifier.height(26.dp))
        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                disabledLabelColor = Color.Cyan,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            placeholder = {
                Text(text = "Email")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
          modifier = Modifier.fillMaxWidth(),
          value = password,
          colors = TextFieldDefaults.textFieldColors(
              cursorColor = Color.Black,
              disabledLabelColor = Color.Cyan,
              focusedIndicatorColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Transparent
          ),
          onValueChange = {
              password = it
          },
          shape = RoundedCornerShape(8.dp),
          singleLine = true,
          placeholder = {
              Text(text = "Password")
          }
        )
        Button(
            onClick = {
                scope.launch {
                    viewModel.loginUser(email, password)
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
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier
                .padding(15.dp)
                .clickable {
                     viewModel.onEvent(NavigateUI.Navigate(Screens.SignupScreen))
                },
            text = "Already Have an account? login In",
            fontWeight = FontWeight.Bold, color = Color.Black, fontFamily = FontFamily.SansSerif
        )
    }
}