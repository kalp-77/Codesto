package com.example.codemaster.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codemaster.R
import com.example.codemaster.ui.theme.CodeMasterTheme
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextBox(
    platform: String,
    username: String,
    scope: CoroutineScope,
) {

    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        CustomDialog(
            platform = platform,
            scope = scope,
            onDismiss = { openDialog = false }
        )
    }
    Card (
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(20.dp),
        colors = CardDefaults.cardColors(Color.LightGray),
        onClick = { openDialog = true }
    ){
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "profile",
                    modifier = Modifier
                        .height(90.dp)
                        .width(90.dp)
                        .padding(15.dp)
                )
                Text(
                    text = platform,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
            Text(
                text = username,
                modifier = Modifier.padding(start = 15.dp, bottom = 15.dp),
                color = Color.Black
            )
        }
    }
}


@Preview
@Composable
fun MyPreview(){
    CodeMasterTheme {
//        TextBox(username = "Codeforces")
    }
}