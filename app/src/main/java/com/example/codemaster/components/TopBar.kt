package com.example.codemaster.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.codemaster.R
import com.example.codemaster.ui.screens.codeforces_ratingchange.font

@Composable
fun TopBar(
    onOpenClick: () -> Unit,
){
    TopAppBar(
        title = {
            Text(
                text = "Codesto",
                color = Color.White,
                fontFamily = font,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        },
        modifier = Modifier
            .height(70.dp),
        navigationIcon = {
            IconButton(
                onClick =  onOpenClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon__menu),
                    contentDescription = "menu_icon",
                    tint = Color.White
                )
            }
        },
        elevation = 0.dp,
        backgroundColor = Color.Black
    )
}