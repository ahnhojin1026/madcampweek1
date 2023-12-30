package com.example.madcampcom1.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.madcampcom1.component.ContactGroupHeader
import com.example.madcampcom1.component.ContactItem
import com.example.madcampcom1.component.PreviewContactGroupHeader
import com.example.madcampcom1.component.PreviewContactItem
import com.example.madcampcom1.viewModel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ContactScreen(
    contactViewModel: ContactViewModel
) {

    val uiState by contactViewModel.uiState.collectAsState()

    Scaffold(floatingActionButton = {
        FloatingActionButton(shape = CircleShape, onClick = { /*TODO*/ }) {
            Icon(Icons.Default.Add, "")
        }
    }) {
        LazyColumn(modifier = Modifier.padding(it), content = {
            uiState.contactMap.forEach { (key, value) ->
                stickyHeader {
                    ContactGroupHeader(key = key.toString())
                }

                items(value) { item ->
                    ContactItem(
                        contactEntity = item,
                        onClickItem = { contactViewModel.onItemClicked(item.id) },
                        isExpanded = contactViewModel.isExpanded(item.id)
                    )
                }
            }
        })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PreviewContactGroup() {
    LazyColumn(content = {
        stickyHeader {
            PreviewContactGroupHeader()
        }

        item {
            PreviewContactItem()
        }
    })
}