package com.example.codemaster.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codemaster.R
import com.example.codemaster.ui.theme.BlueG
import com.example.codemaster.ui.theme.GreenG


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockCard(
    block1 : String,
    block2 : String,
    icon1: Int,
    icon2: Int,
    blockTextColor1: Color,
    blockTextColor2: Color,
    blockColor1: Color,
    blockColor2: Color,
    onClickBlock1 : ()-> Unit,
    onClickBlock2: () -> Unit,
){
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            modifier = Modifier,
            text = "My Learning",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier,
            text = "A quick access feature related to learn more!",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(blockColor1),
            onClick = onClickBlock1

        ) {
            Row(
                Modifier
                    .padding(5.dp)
                    .fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                ) {
                    Image(
                        modifier = Modifier.padding(5.dp),
                        painter = painterResource(id = icon1),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = block1,
                    fontSize = 12.sp,
                    color = blockTextColor1
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(blockColor2),
            onClick = onClickBlock2
        ) {
            Row(
                Modifier
                    .padding(5.dp)
                    .fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                ) {
                    Image(
                        modifier = Modifier.padding(5.dp),
                        painter = painterResource(id = icon2),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = block2,
                    fontSize = 12.sp,
                    color = blockTextColor2
                )
            }
        }
    }
}