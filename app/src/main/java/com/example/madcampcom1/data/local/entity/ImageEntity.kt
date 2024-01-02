package com.example.madcampcom1.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "image_table")
data class ImageEntity (
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "image_uri")
    val uri: String,
    @ColumnInfo(name = "image_info")
    val info: String
)