package com.example.madcampcom1.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.madcampcom1.data.local.entity.ContactEntity

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact_table")
    fun getAll(): List<ContactEntity>

    @Insert
    fun insertContact(contact: ContactEntity)

    @Update
    fun updateContact(contact: ContactEntity)

    @Delete
    fun deleteContact(contact: ContactEntity)

    @Query("SELECT EXISTS(SELECT * FROM contact_table WHERE id = :id)")
    fun isExist(id: String): Boolean
}