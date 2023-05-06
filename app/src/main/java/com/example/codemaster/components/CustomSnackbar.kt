package com.example.codemaster.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codemaster.R
import com.example.codemaster.ui.theme.GreenUpArrow
import com.example.codemaster.ui.theme.PurpleNavy20

@Preview
@Composable
fun CustomSnackbar() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(PurpleNavy20)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.12f)
                    .size(20.dp)
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(GreenUpArrow)
                        .align(Alignment.Center),
                    painter = painterResource(id  = R.drawable.up_arrow),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(1f),
                    fontSize = 10.sp,
                    color = Color.White,
                    text = "Your rating increased by +18 from last contest"
                )
                Text(
                    modifier = Modifier.fillMaxWidth(1f),
                    fontSize = 10.sp,
                    color = Color.White,
                    text = "Good Job!!"
                )
            }


        }
    }
}