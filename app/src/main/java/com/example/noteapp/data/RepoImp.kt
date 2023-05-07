package com.example.noteapp.data

import com.example.noteapp.data.Repo
import com.example.noteapp.data.local.Note
import com.example.noteapp.data.local.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoImp @Inject constructor(
    private val dao: NoteDao
) : Repo {
    override fun getNote(): Flow<List<Note>> {
        return dao.getNote()
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note)
    }
    override suspend fun addNote(note: Note) {
        dao.addNote(note)
    }
}