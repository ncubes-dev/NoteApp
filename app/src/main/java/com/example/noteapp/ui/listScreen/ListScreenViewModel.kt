package com.example.noteapp.ui.listScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.Constants
import com.example.noteapp.UiEvents
import com.example.noteapp.data.Repo
import com.example.noteapp.data.local.Note
import com.example.noteapp.ui.addScreen.AddScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private  val repo :Repo
):ViewModel() {
    var deletedNote: Note?=null
   val notes=repo.getNote()
    private val _uiEvent  = MutableSharedFlow<UiEvents>()
    val uiEvent=_uiEvent.asSharedFlow()
    fun onEvent(event: ListScreenEvents){
        when(event){
            is ListScreenEvents.OnDeleteClick->{
                deletedNote=event.note
                viewModelScope.launch {
                    repo.deleteNote(event.note)
                    _uiEvent.emit(UiEvents.ShowSnackbar("Deleted", "Undo"))
                }
            }
            is ListScreenEvents.OnNoteClick->{
                viewModelScope.launch {
                    _uiEvent.emit(UiEvents.Navigate(Constants.DETAIL+"/${event.note.note}/${event.note.id}"))
                }
            }
            is ListScreenEvents.AddButtonClick->{
                viewModelScope.launch {
                    _uiEvent.emit(UiEvents.Navigate(Constants.ADD))
                }
            }
            is ListScreenEvents.OnUndo->{
                viewModelScope.launch {
                    deletedNote?.let {
                        repo.addNote(
                            it
                        )
                    }
                }
            }
        }
    }
}