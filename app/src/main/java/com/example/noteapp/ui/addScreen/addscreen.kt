package com.example.noteapp.ui.addScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.noteapp.UiEvents
import com.example.noteapp.data.local.Note
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SimpleDateFormat")
@Composable
fun AddScreen(
    value1: String,
    onValueChange1: (String) -> Unit,
    value2: String,
    onValueChange2: (String) -> Unit,
    viewModel: AddScreenViewModel = hiltViewModel(),
    clearTextFields: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState
    ) {}
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvents.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )
                }
                is UiEvents.ClearTextField -> {
                    clearTextFields()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState(),true),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value1,
            onValueChange = onValueChange1,
            label = { Text(text = "Note") })
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value2,
            onValueChange = onValueChange2,
            label = { Text(text = "Optional description") })
        Button(
            onClick = {
                val sdf = SimpleDateFormat("dd-MM-yyyy  HH:mm")
                val currentDateAndTime = sdf.format(Date())
                if (value1.isNotEmpty()) {
                    viewModel.onEvent(
                        AddScreenEvents.OnAddButtonPress(
                            Note(
                                note = value1,
                                description = value2,
                                id = 0,
                                date = currentDateAndTime
                            )
                        )
                    )
                }
                else{
                    viewModel.onEvent(
                        AddScreenEvents.OnAddButtonPress(
                            null
                        )
                    )
                }


            }, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) { Text(text = "Save") }
    }
}
