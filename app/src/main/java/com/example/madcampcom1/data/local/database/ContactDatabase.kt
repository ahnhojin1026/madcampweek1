package com.example.madcampcom1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.madcampcom1.data.local.converter.NumberListConverter
import com.example.madcampcom1.data.local.dao.ContactDao
import com.example.madcampcom1.data.local.entity.ContactEntity

@Database(entities = [ContactEntity::class], version = 4, exportSchema = false)
@TypeConverters(NumberListConverter::class)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}