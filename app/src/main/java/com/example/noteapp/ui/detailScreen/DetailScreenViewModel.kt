package com.example.noteapp.ui.detailScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.Constants
import com.example.noteapp.UiEvents
import com.example.noteapp.data.Repo
import com.example.noteapp.data.local.Note
import com.example.noteapp.ui.listScreen.ListScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val repo: Repo
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<UiEvents>()
    val uiEvent = _uiEvent.asSharedFlow()
    fun onEvent(event: DetailScreenEvents) {
        when (event) {
            is DetailScreenEvents.OnUpdateClick -> {
                viewModelScope.launch {
                    repo.updateNote(event.note)//date and note must be updated
                    _uiEvent.emit(UiEvents.ShowSnackbar("Updated"))
                }
            }
        }
    }
}