package com.example.noteapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.ui.addScreen.AddScreen
import com.example.noteapp.ui.detailScreen.DetailScreen
import com.example.noteapp.ui.listScreen.ListScreen
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var value1 by remember { mutableStateOf("") }
                    var value2 by remember { mutableStateOf("") }
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Constants.LIST
                    ) {
                        composable(route = Constants.LIST) {
                            ListScreen(navController = navController)
                        }
                        composable(route = Constants.ADD) {
                            AddScreen(
                                value1 = value1,
                                value2 = value2,
                                onValueChange1 = { value1 = it },
                                onValueChange2 = { value2 = it },
                                clearTextFields={
                                    value1=""
                                    value2=""
                                }
                            )
                        }
                        composable(
                            route = Constants.DETAIL + "/{note}/{id}",
                            arguments = listOf(
                                navArgument(name = "id") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                },

                                navArgument(name = "note") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                })
                        ) {
                            it.arguments?.let { it2 ->
                                it2.getString("note")?.let { it1 ->
                                    DetailScreen(
                                        note = it1,
                                        id = it2.getInt("id"),

                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
