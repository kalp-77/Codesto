package com.example.codemaster.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.codemaster.R
import com.example.codemaster.ui.screens.codeforces_ratingchange.font
import com.example.codemaster.ui.theme.Darkblack30

@Composable
fun TopBar(
    onOpenClick: () -> Unit,
){
    val icon = remember { mutableStateOf( R.drawable.icon__menu) }
    TopAppBar(
        modifier = Modifier.height(70.dp),
        navigationIcon = {
            IconButton(
                onClick =  onOpenClick,
            ) {
                Icon(
                    modifier = Modifier.padding(top = 20.dp, start =10.dp),
                    painter = painterResource(id = icon.value),
                    contentDescription = "menu_icon",
                    tint = Color.White
                )
            }
        },
        title = {
            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = "Codesto",
                color = Color.White,
                fontFamily = font,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        },

//        elevation = 0.dp,
        backgroundColor = Darkblack30
    )
}