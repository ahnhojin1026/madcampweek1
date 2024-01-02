package com.example.madcampcom1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.madcampcom1.data.local.dao.ImageDatabaseDao
import com.example.madcampcom1.data.local.entity.ImageEntity

@Database(entities = [ImageEntity::class],version = 1, exportSchema = false)
abstract class ImageDatabase :RoomDatabase(){
    abstract fun imageDao() : ImageDatabaseDao
}