package com.example.noteapp.ui.addScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.UiEvents
import com.example.noteapp.data.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddScreenViewModel @Inject constructor(
    private val repo: Repo
) : ViewModel() {
    val notes=repo.getNote()
   private val _uiEvent  = MutableSharedFlow<UiEvents>()
    val uiEvent=_uiEvent.asSharedFlow()
    fun onEvent(event:AddScreenEvents){
        when(event){
            is AddScreenEvents.OnAddButtonPress->{
                viewModelScope.launch {
                    if (event.note==null){
                        _uiEvent.emit(UiEvents.ShowSnackbar("Note cannot be empty"))
                    }
                    else{
                        repo.addNote(event.note!!)
                        _uiEvent.emit(UiEvents.ClearTextField)
                        _uiEvent.emit(UiEvents.ShowSnackbar("Saved"))
                    }
                }
            }
        }
    }
}
