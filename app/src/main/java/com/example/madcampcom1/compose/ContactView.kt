package com.example.madcampcom1.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.madcampcom1.viewModel.ContactViewModel

@Composable
fun ContactView(
    contactViewModel: ContactViewModel
) {

    val uiState by contactViewModel.uiState.collectAsState()

    LazyColumn(content = {
        this.items(uiState.list.size) {
            Text(text = uiState.list[it].name)
        }
    })
}