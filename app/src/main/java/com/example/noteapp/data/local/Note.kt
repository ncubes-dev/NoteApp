package com.example.noteapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes_table")
data class Note (
     val note: String,
     val date:String,
     val description: String?=null,
     @PrimaryKey(autoGenerate = true)
     val id:Int
)