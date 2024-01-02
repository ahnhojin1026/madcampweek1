package com.example.madcampcom1.data.repository

import com.example.madcampcom1.data.local.dao.ContactDao
import com.example.madcampcom1.data.local.entity.ContactEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactRepository @Inject constructor(private val contactDao: ContactDao) {

    fun getAll(): Flow<List<ContactEntity>> = contactDao.getAll()
    suspend fun addContact(contact: ContactEntity) = contactDao.insertContact(contact)
    suspend fun addContacts(contacts: List<ContactEntity>) = contactDao.insertContacts(contacts)
    suspend fun updateContact(contact: ContactEntity) = contactDao.updateContact(contact)
    suspend fun deleteContact(contact: ContactEntity) = contactDao.deleteContact(contact)
    suspend fun deleteAll() = contactDao.deleteAll()
}