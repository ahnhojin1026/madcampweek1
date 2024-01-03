package com.example.madcampcom1.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.madcampcom1.component.ContactDetailInfo
import com.example.madcampcom1.component.ContactDialog
import com.example.madcampcom1.component.ContactNumberList
import com.example.madcampcom1.component.SubTopBar
import com.example.madcampcom1.ui.theme.Background
import com.example.madcampcom1.ui.theme.Red
import com.example.madcampcom1.viewModel.ContactViewModel

@Composable
fun ContactDetailScreen(contactViewModel: ContactViewModel, onPop: () -> Unit) {
    val uiState by contactViewModel.uiState.collectAsState()
    val detailValue = uiState.detailValue!!
    val isDeleteDialogOpened = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SubTopBar(onNavigationIconClick = onPop) {
                IconButton(onClick = { }) {
                    Icon(Icons.Rounded.Edit, "edit")
                }

                IconButton(onClick = {
                    isDeleteDialogOpened.value = true
                }) {
                    Icon(Icons.Rounded.Delete, "delete", tint = Red)
                }
            }
        }, containerColor = Background
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(top = 20.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                ContactDetailInfo(
                    name = detailValue.name, defaultNumber = detailValue.defaultNumber()
                )
            }

            item {
                ContactNumberList(detailValue.numbers) { number ->
                    detailValue.copy(numbers = listOf(number) + (detailValue.numbers - number))
                        .let { newDetailValue ->
                            contactViewModel.updateContact(newDetailValue)
                            contactViewModel.setDetailValue(newDetailValue)
                        }
                }
            }
        }

        if (isDeleteDialogOpened.value) ContactDialog("연락처를 삭제할까요?", onDismissRequest = {
            isDeleteDialogOpened.value = false
        }, confirmText = "삭제") {
            contactViewModel.removeContact(detailValue)
            onPop()
        }
    }
}