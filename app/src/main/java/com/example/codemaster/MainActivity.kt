package com.example.codemaster

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.codemaster.components.BottomNav
import com.example.codemaster.components.BottomNavGraph
import com.example.codemaster.navigation.BottomNavScreens
import com.example.codemaster.navigation.NavigationGraph
import com.example.codemaster.navigation.Screens
import com.example.codemaster.ui.theme.CodeMasterTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeMasterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavigationGraph(navController)

//                    val bottomNavigationItems = listOf(
//                        BottomNavScreens.ContestsScreen,
//                        BottomNavScreens.CodechefScreen,
//                        BottomNavScreens.CodeforcesScreen,
//                        BottomNavScreens.LeetcodeScreen
//                    )
//                    Scaffold(
//                        bottomBar = { BottomNav(navController = navController, items = bottomNavigationItems ) },
//                    ) {
////                        BottomNavGraph(navController)
//
//                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodeMasterTheme {
        Greeting("Android")
    }
}