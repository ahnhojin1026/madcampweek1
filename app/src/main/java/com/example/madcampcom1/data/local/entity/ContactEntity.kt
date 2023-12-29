package com.example.madcampcom1.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "contactId") val contactId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "number") val number: String
)
