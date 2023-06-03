package com.example.codemaster.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.model.codeforces_model.user_info.UserInfoResult
import com.example.codemaster.ui.screens.codeforces.CodeforcesViewModel
import com.example.codemaster.ui.screens.codeforces.FriendsCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun BottomSheetContent(
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    viewModel: CodeforcesViewModel = hiltViewModel()
) {

    val handle = remember { mutableStateOf("") }
    val getUser = viewModel.friendGet.collectAsState()
    val saveUser = viewModel.friendSave.collectAsState()
    val isVisible by remember {
        derivedStateOf {
            handle.value.isNotBlank()
        }
    }
    val flag = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.padding(10.dp)) {
        Box(modifier = Modifier.fillMaxWidth()){
            Divider(
                modifier = Modifier
                    .height(2.dp)
                    .width(80.dp)
                    .align(Alignment.Center),
                color = Color.LightGray

            )
        }
        Spacer(modifier = Modifier.height(35.dp))
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Compete with friends",
                color = Color.White,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = "Track your friends progress",
                color = Color.DarkGray,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = handle.value,
                onValueChange = { handle.value = it },
                shape = RoundedCornerShape(8.dp),
                placeholder = { androidx.compose.material3.Text(text = "search handle") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        viewModel.getFriends(handle.value)
                        flag.value = true
                    }),
                leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Account") },
                trailingIcon = {
                    if (isVisible) {
                        IconButton(
                            onClick = {
                                keyboardController?.hide()
                                viewModel.getFriends(handle.value)

                                flag.value = true
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Done, contentDescription = "Done", tint = Color.Black)
                        }
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.LightGray,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )

            )
            Spacer(modifier = Modifier.height(20.dp))
            FriendResult(state = getUser, flag = flag)
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    getUser.value?.data?.let {
                        viewModel.saveFriends(it)

                        scope.launch {
                            sheetState.hide()

                        }
                        handle.value = ""
                        flag.value = false
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Add",
                    color = Color.Black,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }

    }

}

@Composable
fun FriendResult(
    state: State<Response<UserInfoResult>?>,
    flag: MutableState<Boolean>
) {
    if(flag.value) {
        when (state.value!!) {
            is Response.Loading -> {
                Loading()
            }

            is Response.Success -> {
                FriendsCard(
                    profileIcon = state.value?.data?.avatar,
                    username = state.value?.data?.handle,
                    rank = state.value?.data?.rank,
                    rating = state.value?.data?.rating,
                    activity = state.value?.data?.lastOnlineTimeSeconds.toString()
                )
            }

            is Response.Failure -> {
                Text(
                    text = "No results found!",
                    color = Color.LightGray,
                    fontSize = 13.sp
                )
            }
        }
    }
    else {
        Text(
            text = "No results found!",
            color = Color.LightGray,
            fontSize = 13.sp
        )
    }
}
