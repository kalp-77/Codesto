package com.example.codemaster.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.ui.screens.platform.PlatformViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    platform: String,
    onDismiss: ()->Unit,
    viewModel: PlatformViewModel = hiltViewModel(),
    scope : CoroutineScope,
) {
    var username by remember { mutableStateOf("") }
    val platformState by viewModel.platformState.collectAsState(initial = null)
    val context = LocalContext.current

    LaunchedEffect(key1 = platformState?.isSuccess) {
        if (platformState?.isSuccess?.isNotEmpty() == true) {
            val success = platformState?.isSuccess
            Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
        }
    }
    LaunchedEffect(key1 = platformState?.isFailure) {
        if (platformState?.isFailure?.isNotBlank() == true) {
            val error = platformState?.isFailure
            Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
        }
    }

    Dialog(
        onDismissRequest =  { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier.padding(8.dp),
        ) {
            Column(
                Modifier
                    .background(Color.White)
            ) {
                Text(
                    text = platform,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 20.sp
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it }, modifier = Modifier.padding(8.dp),
                    label = { Text("Enter Username") },
                    colors = TextFieldDefaults.textFieldColors(Color.Black)
                )
                Row {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.saveUserName(platform, username)
                            }
                            onDismiss()
                        },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Save")
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    if (platformState?.isLoading == true) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}