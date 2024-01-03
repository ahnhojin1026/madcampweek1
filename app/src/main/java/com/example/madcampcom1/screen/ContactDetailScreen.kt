package com.example.madcampcom1.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.madcampcom1.component.ContactDetailInfo
import com.example.madcampcom1.component.ContactMsgDialog
import com.example.madcampcom1.component.ContactNumberList
import com.example.madcampcom1.component.SubTopBar
import com.example.madcampcom1.ui.theme.Background
import com.example.madcampcom1.ui.theme.Red
import com.example.madcampcom1.viewModel.ContactViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactDetailScreen(contactViewModel: ContactViewModel, onPop: () -> Unit) {
    val uiState by contactViewModel.uiState.collectAsState()
    val detailValue = uiState.detailValue!!
    val isDeleteDialogOpened = remember { mutableStateOf(false) }
    val dialogValue = remember { mutableStateOf<String?>(null) }

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
        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
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
                    ContactNumberList(detailValue.numbers) { dialogValue.value = it }
                }
            }
        }

        if (isDeleteDialogOpened.value) ContactMsgDialog(
            message = "연락처를 삭제할까요?",
            onDismissRequest = { isDeleteDialogOpened.value = false },
            confirmText = "삭제"
        ) {
            contactViewModel.removeContact(detailValue)
            onPop()
        }

        if (dialogValue.value != null) ContactMsgDialog(
            message = "기본 번호로 설정할까요?",
            onDismissRequest = { dialogValue.value = null },
            confirmText = "설정"
        ) {
            dialogValue.value!!.let { number ->
                detailValue.copy(numbers = listOf(number) + (detailValue.numbers - number))
                    .let { newDetailValue ->
                        contactViewModel.updateContact(newDetailValue)
                        contactViewModel.setDetailValue(newDetailValue)
                    }
            }
        }
    }
}