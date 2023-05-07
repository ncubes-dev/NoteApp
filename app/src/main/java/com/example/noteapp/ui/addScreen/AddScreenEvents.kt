package com.example.noteapp.ui.addScreen

import com.example.noteapp.data.local.Note

sealed class AddScreenEvents {
    data class OnAddButtonPress(var note: Note?): AddScreenEvents()
}