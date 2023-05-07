package com.example.noteapp.ui.listScreen

import com.example.noteapp.data.local.Note

sealed class ListScreenEvents{
    data class OnDeleteClick(val note: Note):ListScreenEvents()
    data class OnNoteClick(val note: Note):ListScreenEvents()
    object OnUndo:ListScreenEvents()
    data class AddButtonClick(val route: String):ListScreenEvents()
}
