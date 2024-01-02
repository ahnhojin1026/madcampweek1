package com.example.madcampcom1.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.madcampcom1.data.local.entity.ContactEntity

@Composable
fun ContactDetailScreen(contactId: Int) {
    Text(contactId.toString())
}