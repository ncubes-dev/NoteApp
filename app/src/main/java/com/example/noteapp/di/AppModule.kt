package com.example.noteapp.di

import android.app.Application
import androidx.room.Room
import com.example.noteapp.data.Repo
import com.example.noteapp.data.local.NoteDatabase
import com.example.noteapp.data.RepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "notes_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepo(db: NoteDatabase): Repo {
        return RepoImp(db.dao)
    }
}