package com.example.codemaster.ui.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.codemaster.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ContestsDetailsDisplayScreen(){


    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            Modifier,
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.image_work),
                contentDescription = "illustration"
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column (
            modifier = Modifier.padding(30.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ){
                Text(
                    text = "data",
                    modifier = Modifier,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Reverse Coding", modifier = Modifier.padding(10.dp), fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))

        ConstraintLayout(
            modifier = Modifier.padding(0.dp),

            ){
            val (recordCircle, textStart, divider) = createRefs()
            val (circle, textEnd) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.icon__recordcircle),
                contentDescription = "null",
                modifier = Modifier
                    .constrainAs(recordCircle){
                        top.linkTo(parent.top)
                        bottom.linkTo(divider.top)
                        start.linkTo(circle.start)
                        end.linkTo(circle.end)
                    }
            )
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .background(Color(0xFF000000))
                    .height(60.dp)
                    .constrainAs(divider) {
                        bottom.linkTo(circle.top)
                        top.linkTo(recordCircle.bottom)
                        start.linkTo(circle.start)
                        end.linkTo(circle.end)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.icon_circle),
                contentDescription = "null",
                modifier = Modifier
                    .constrainAs(circle){
                        bottom.linkTo(parent.bottom)
                        top.linkTo(divider.bottom)
                    }
            )
            Row(
                modifier = Modifier
                    .constrainAs(textStart){
                        start.linkTo(recordCircle.end, margin = 10.dp)
                        top.linkTo(recordCircle.top)
                        bottom.linkTo(recordCircle.bottom)
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ){
                Text(text = "28 Apr, 2023", modifier = Modifier,  fontSize = 16.sp)
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "20:00", modifier = Modifier,  fontSize = 16.sp)
            }
            Row(
                modifier = Modifier
//                    .padding(20.dp)
                    .constrainAs(textEnd){
                        start.linkTo(recordCircle.end, margin = 10.dp)
                        top.linkTo(circle.top)
                        bottom.linkTo(circle.bottom)
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ){
                Text(text = "28 Apr, 2023", modifier = Modifier,  fontSize = 16.sp)
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "20:00", modifier = Modifier,  fontSize = 16.sp)
            }

        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom
        ){
            Icon(
                imageVector = Icons.Default.Alarm,
                contentDescription ="alarm" ,
                Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(50.dp))
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "calendar", Modifier.size(30.dp))
            Spacer(modifier = Modifier.width(50.dp))
            Icon(imageVector = Icons.Default.Info, contentDescription = "url", Modifier.size(30.dp))
        }
    }
}