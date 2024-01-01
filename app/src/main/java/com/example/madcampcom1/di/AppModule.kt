package com.example.madcampcom1.di

import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import com.example.madcampcom1.data.local.dao.ContactDao
import com.example.madcampcom1.data.local.dao.NoteDatabaseDao
import com.example.madcampcom1.data.local.database.ContactDatabase
import com.example.madcampcom1.data.local.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideContactDao(contactDatabase: ContactDatabase): ContactDao =
        contactDatabase.contactDao()
    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDatabaseDao =
        noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ContactDatabase =
        Room.databaseBuilder(
            context,
            ContactDatabase::class.java,
            "contact_database"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun providenoteDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "notes_database"
        ).fallbackToDestructiveMigration().build()
    @Singleton
    @Provides
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver =
        context.contentResolver
}