package com.example.codemaster.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.codemaster.navigation.Screens
import com.example.codemaster.ui.screens.codeforces_ratingchange.font
import com.example.codemaster.utils.NavigateUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
    TopAppBar(
        title = { Text(text = "CodeMaster", color = Color(0xFF2A265C), fontFamily = font, textAlign = TextAlign.Center) },
        modifier = Modifier.height(50.dp).background(Color.White),
        actions = {
            IconButton(
                onClick = {  },
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = Color(0xFF2A265C)
                )
            }
        },
        elevation = 5.dp,
        backgroundColor = Color.White
    )
}