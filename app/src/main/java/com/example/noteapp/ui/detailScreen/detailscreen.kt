package com.example.noteapp.ui.detailScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.UiEvents
import com.example.noteapp.data.local.Note
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SimpleDateFormat")
@Composable
fun DetailScreen(
    viewModel: DetailScreenViewModel = hiltViewModel(),
    id: Int,
    note: String,
) {

    val scaffoldState = rememberScaffoldState()
    var value by remember { mutableStateOf(note) }
    var updateRequest by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvents.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            if (updateRequest) {
                FloatingActionButton(onClick = {
                    val sdf = SimpleDateFormat("dd-MM-yyyy  HH:mm")
                    val currentDateAndTime = sdf.format(Date())
                    viewModel.onEvent(
                        DetailScreenEvents.OnUpdateClick(
                            Note(
                                note = value,
                                id = id,
                                date = currentDateAndTime
                            )
                        )
                    )
                }) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                }
            } else Unit
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            TextField(
                value = value,
                onValueChange = {
                    updateRequest = true
                    value = it
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}