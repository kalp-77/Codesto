package com.example.codemaster.ui.screens.codeforces

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.codemaster.R
import com.example.codemaster.components.BottomSheetContent
import com.example.codemaster.ui.theme.Comrades100
import com.example.codemaster.ui.theme.ProfileBackground100
import com.example.codemaster.utils.rankColors
import kotlinx.coroutines.launch
import java.util.Locale
import androidx.compose.material.DismissState as DismissState1

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FriendsScreen(
    viewModel : CodeforcesViewModel = hiltViewModel()
) {

    val friendsListState = viewModel.friendList.collectAsState().value
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy
        )
    )
    val scope = rememberCoroutineScope()


    ModalBottomSheetLayout(
        sheetContent = { BottomSheetContent(sheetState, scope) },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape( topStart = 15.dp, topEnd = 15.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
        sheetBackgroundColor = Color.Black
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(10.dp)
        ) {

            Spacer(modifier = Modifier.height((20.dp)))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ComradesZone",
                    modifier = Modifier,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.montserrat))
                )
                Box(
                    modifier = Modifier.wrapContentSize()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Cyan)
                        .clickable(onClick = {
                        scope.launch {
                            sheetState.show()
                        }
                    }),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 7.dp),
                        text = "ADD",
                        color = Color.Black,
                        fontSize = 10.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            LazyColumn {
                if (!friendsListState.isNullOrEmpty()) {
                    items(items = friendsListState, key = { index -> index.handle!! }) { item ->
                        val currentItem by rememberUpdatedState(item)
                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    viewModel.deleteFriend(currentItem)
                                }
                                true
                            }
                        )
                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(DismissDirection.EndToStart),
                            dismissThresholds = {
                                if (it == DismissDirection.StartToEnd)
                                    FractionalThreshold(0.86f)
                                else FractionalThreshold(0.75f)
                            },
                            background = {
                                SwipeBackground(dismissState = dismissState)
                            }
                        ) {
                            FriendsCard(
                                profileIcon = item.avatar,
                                username = item.handle,
                                rank = item.rank,
                                rating = item.rating,
                                activity = item.lastOnlineTimeSeconds.toString(),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FriendsCard(
    profileIcon: String?,
    username: String?,
    rank: String?,
    rating: Int?,
    activity: String?,
) {
    val profile = rememberAsyncImagePainter(model = profileIcon)
    val textColor = rankColors.find { it.rank == rank }?.color ?: Color.LightGray


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(Color.Black)
            .height(70.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Comrades100)
                .height(70.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(CircleShape)
                            .background(ProfileBackground100)
                            .size(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(36.dp)
                                .background(ProfileBackground100)
                                .align(Alignment.Center),
                            painter = profile,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = username.toString()
                            )
                            Text(
                                text = activity.toString(),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = processRank(rank.toString()),
                        fontSize = 12.sp,
                        color = textColor
                    )
                    Text(
                        text = rating.toString(),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}


@Composable
@OptIn(ExperimentalMaterialApi::class)
fun SwipeBackground(dismissState: DismissState1) {
    val direction = remember {
        dismissState.dismissDirection
    }

    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.Black
            DismissValue.DismissedToEnd -> Color.Black
            DismissValue.DismissedToStart -> Color.Black
        }, label = ""
    )
    val icon = remember(direction) {
        when (direction) {
            DismissDirection.EndToStart -> Icons.Default.Delete
            else -> { Icons.Default.Delete}
        }
    }
    val iconColor = if(dismissState.targetValue == DismissValue.Default) Color.LightGray else ProfileBackground100

    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f, label = ""
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            icon,
            contentDescription = "Localized description",
            modifier = Modifier.scale(scale),
            tint = iconColor
        )
    }
}

fun processRank(s: String): String {

    val words = s.split(" ")
    return if (words.size == 1) {
        words[0].capitalize(Locale.ROOT) // If single word, return the word itself
    } else {
        val result = StringBuilder()
        for (word in words) {
            result.append(word[0].uppercase()) // Append the first letter of each word
        }
        result.toString() // Return the resulting string
    }
}