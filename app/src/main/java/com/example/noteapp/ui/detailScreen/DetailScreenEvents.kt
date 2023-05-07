package com.example.noteapp.ui.detailScreen

import com.example.noteapp.data.local.Note

sealed class DetailScreenEvents{
    data class OnUpdateClick(var note: Note):DetailScreenEvents() }
