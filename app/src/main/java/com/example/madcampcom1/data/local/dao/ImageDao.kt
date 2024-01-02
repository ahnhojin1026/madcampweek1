package com.example.madcampcom1.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.madcampcom1.data.local.entity.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDatabaseDao{
    @Query("SELECT * from image_table")
    fun getImages(): Flow<List<ImageEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imageEntity: ImageEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(imageEntity: ImageEntity)

    @Query("DELETE from image_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteImage(imageEntity: ImageEntity)
}
