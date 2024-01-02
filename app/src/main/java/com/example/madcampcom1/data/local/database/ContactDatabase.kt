package com.example.madcampcom1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.madcampcom1.data.local.dao.ContactDao
import com.example.madcampcom1.data.local.entity.ContactEntity

@Database(entities = [ContactEntity::class], version = 3, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}