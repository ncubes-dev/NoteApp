package com.example.noteapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes_table")
    fun getNote(): Flow<List<Note>>

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

//    @Query("SELECT * FROM notes_table WHERE id=:id")
//    suspend fun getOneNote(id: Int):Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)
}