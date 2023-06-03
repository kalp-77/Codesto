package com.example.codemaster.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.ui.screens.platform.PlatformViewModel
import com.example.codemaster.ui.theme.Comrades100
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SaveUsernameCard(
    textValue : MutableState<String>,
    platform: String,
    viewModel : PlatformViewModel = hiltViewModel()
) {

    val flag = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val isVisible by remember {
        derivedStateOf {
            textValue.value.isNotBlank()
        }
    }
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
                    text = platform.toUpperCase(Locale.ROOT),
                    fontSize = 10.sp,
                    color = Color.LightGray
                )
                BasicTextField(
                    value = textValue.value,
                    onValueChange = {
                        textValue.value = it
                    },
                    textStyle = TextStyle(fontSize = 12.sp, color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 3.dp),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            flag.value = true
                        }
                    ),
                    decorationBox = {
                        Box {
                            if (textValue.value.isBlank()) {
                                Text(
                                    text = "Enter $platform username",
                                    style = TextStyle(
                                        color = Color.LightGray,
                                        fontSize = 12.sp
                                    )
                                )
                            }
                            it()
                        }
                    }
                )
            }
        }
    }
}