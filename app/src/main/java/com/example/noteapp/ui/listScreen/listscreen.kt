package com.example.noteapp.ui.listScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.Constants
import com.example.noteapp.UiEvents
import com.example.noteapp.data.local.Note
import com.example.noteapp.ui.theme.Shapes
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreen(
    viewModel: ListScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvents.ShowSnackbar -> {
                    val result =scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )
                    when (result){
                        SnackbarResult.ActionPerformed->{
                            viewModel.onEvent(ListScreenEvents.OnUndo)
                        }
                    }
                }
                is UiEvents.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }
    val list by viewModel.notes.collectAsState(initial = emptyList())
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(
                    ListScreenEvents.AddButtonClick(
                        Constants.ADD
                    )
                )
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)
        ) {
            LazyVerticalGrid(columns = GridCells.Adaptive(150.dp)) {
                items(list) { it ->
                    CardItem(it, viewModel)
                }

            }
        }
    }
}


@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardItem(note: Note, viewModel: ListScreenViewModel) {
    Card(
        onClick = { viewModel.onEvent(ListScreenEvents.OnNoteClick(note)) },
        elevation = 10.dp,
        shape = Shapes.medium,
        modifier = Modifier
            .padding(4.dp)
            .height(150.dp)
            .shadow(
                ambientColor = Color.Green,
                spotColor = Color.Blue,
                elevation = 25.dp,
                shape = RoundedCornerShape(10.dp)
            ),
//        border = BorderStroke(color = MaterialTheme.colors.primary, width = 2.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(all=3.dp)
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = note.date,
                    modifier = Modifier.weight(1f),
                    fontFamily = FontFamily.Cursive,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Thin
                )
                IconButton(onClick = { viewModel.onEvent(ListScreenEvents.OnDeleteClick(note = note)) }) {
                    Icon(imageVector = Icons.Default.Delete,tint=MaterialTheme.colors.primary, contentDescription = "delete note")
                }
            }
            Text(text = note.note, maxLines = 4)
        }
    }
}

