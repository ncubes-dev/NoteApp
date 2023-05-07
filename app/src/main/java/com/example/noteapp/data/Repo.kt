package com.example.noteapp.data

import androidx.room.*
import com.example.noteapp.data.local.Note
import kotlinx.coroutines.flow.Flow

interface Repo {
    fun getNote(): Flow<List<Note>>

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun addNote(note: Note)
}