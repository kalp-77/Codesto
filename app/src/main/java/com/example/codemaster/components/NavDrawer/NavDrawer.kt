package com.example.codemaster.components.NavDrawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.codemaster.R
import com.example.codemaster.navigation.BottomNavScreens
import com.example.codemaster.navigation.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AnimatedDrawer(
    modifier: Modifier = Modifier,
    state: DrawerState = rememberDrawerState(drawerWidth = 200.dp,),
    drawerContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {

    Layout(
        modifier = Modifier,
        content = {
            drawerContent()
            content()
        },
    ){measurables, constraints ->
        val (drawerContentMeasurable, contentMeasurable) = measurables
        val drawerContentConstraints = Constraints.fixed(
            width = state.drawerWidth.coerceAtMost(constraints.maxWidth.toFloat()).toInt(),
            height = constraints.maxHeight,
        )
        val drawerContentPlaceable = drawerContentMeasurable.measure(drawerContentConstraints)
        val contentConstraints = Constraints.fixed(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        )
        val contentPlaceable = contentMeasurable.measure(contentConstraints)
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ){
            contentPlaceable.placeRelativeWithLayer(
                IntOffset.Zero,
            ) {
                transformOrigin = state.contentTransformOrigin
                scaleX = state.contentScaleX
                scaleY = state.contentScaleY
                translationX = state.contentTranslationX
            }
            drawerContentPlaceable.placeRelativeWithLayer(
                IntOffset.Zero,
            ) {
                translationX = state.drawerTranslationX
                shadowElevation = state.drawerElevation
            }
        }
    }
}

@Composable
fun rememberDrawerState(
    drawerWidth: Dp,
): DrawerState {
    val density = LocalDensity.current.density
    return remember {
       DrawerStateImpl(
            drawerWidth = drawerWidth.value * density,
        )
    }
}

@Composable
private fun DrawerMenuItem(
    item: BottomNavScreens,
    selected: Boolean,
    onItemClick: (BottomNavScreens) -> Unit,
){
    val background = if(selected) Color(0xff2A313C) else Color.Black
    val iconTint = if(selected) Color.White else Color(0xFF4D5362)
    val textColor = if (selected) Color.White else Color(0xffB2B6C2)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onItemClick(item) }
            .background(background),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = "icon",
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = item.title,
                color = textColor,
                textAlign = TextAlign.Start,
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}


@Composable
fun DrawerBody(
    scope: CoroutineScope,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    drawerState: DrawerState
) {
    val items = listOf(
        BottomNavScreens.HomeScreen,
        BottomNavScreens.CodeforcesScreen,
        BottomNavScreens.CodechefScreen,
        BottomNavScreens.LeetcodeScreen,
        BottomNavScreens.PlatformScreen
    )

    Box(
        Modifier
            .padding(top = 10.dp, bottom = 10.dp, start = 5.dp, end = 10.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color(0xFF000000))
            .pointerInput(Unit){
                detectHorizontalDragGestures { change, dragAmount ->
                    if (dragAmount > 0) {
                        scope.launch { drawerState.open() }
                    }
                    if (dragAmount < 0) {
                        scope.launch { drawerState.close() }
                    }
                    change.consumePositionChange()
                }
            }
    ){
        Column(
            Modifier
                .background(Color.Black)
                .fillMaxHeight()
        ) {

            //cross icon
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                IconButton(
                    onClick = onCloseClick,
                    modifier = Modifier.padding(top=8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                        tint = (Color(0xFFA6ADBD))
                    )
                }
            }

            //header(hello user)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start

            ) {
                Column (
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(20.dp)
                ){
                    Text(
                        text = "Hello!",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier,
                        fontSize = 15.sp,
                        color = Color(0xFF878A92)
                    )
                    Text(
                        text = "Mrunmayi",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .padding(top = 5.dp),
                        fontSize = 25.sp,
                        color = Color.White,
                    )
                }
            }

            //nav drawer navigation
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach{ item ->
                Row (
                    modifier = Modifier
                        .clickable {
                            scope.launch { drawerState.close() }
                            navController.navigate(BottomNavScreens.HomeScreen.route)
                        }
                ){
                    DrawerMenuItem(
                        item = item,
                        selected = currentRoute == item.route,
                        onItemClick = {
                            navController.navigate(item.route){
                                navController.graph.startDestinationRoute?.let{ route ->
                                    popUpTo(route){
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        },
                    )
                }
            }
            //logout button
            Row (
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 50.dp),
                ) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                            .background( Color(0xFF4D5362))
                    )
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.logout_icon),
                            contentDescription = "icon",
                            tint = Color(0xFF4D5362),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Logout",
                            modifier = Modifier.padding(start = 10.dp),
                            textAlign = TextAlign.Start,
                            color = Color(0xffBEC3CF),
                            fontSize = 13.sp,
                        )
                    }
                }
            }
        }
    }
}