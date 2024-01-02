package com.example.madcampcom1.data.repository

import com.example.madcampcom1.data.local.dao.ImageDatabaseDao
import com.example.madcampcom1.data.local.entity.ImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ImageRepository @Inject constructor(private val imageDatabaseDao: ImageDatabaseDao){
    suspend fun addImage(image:ImageEntity) = imageDatabaseDao.insert(image)
    suspend fun removeImage(image: ImageEntity) = imageDatabaseDao.deleteImage(image)
    fun getAllImagess(): Flow<List<ImageEntity>> = imageDatabaseDao.getImages().flowOn(Dispatchers.IO).conflate()
}