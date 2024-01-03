package com.example.madcampcom1.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.madcampcom1.data.local.entity.ContactEntity
import com.example.madcampcom1.ui.theme.Background
import com.example.madcampcom1.ui.theme.Surface

@Composable
fun ContactEditDialog(
    editValue: ContactEntity,
    setEditValue: (ContactEntity?) -> Unit,
    onDismiss: () -> Unit,
    onSave: (ContactEntity) -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1F)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                    .background(Surface), contentPadding = PaddingValues(
                    start = 24.dp, top = 44.dp, end = 36.dp, bottom = 44.dp
                ), verticalArrangement = Arrangement.spacedBy(44.dp)
            ) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        ContactFieldGroupHeader(isList = false) { }
                        ContactFieldItem(
                            value = editValue.name,
                            onValueChange = { name -> setEditValue(editValue.copy(name = name)) },
                            isRemovable = false
                        ) { }
                    }
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        ContactFieldGroupHeader(isList = true) { }
                        editValue.numbers.forEach { number ->
                            ContactFieldItem(
                                value = number, onValueChange = { newNumber ->
                                    val numbers = editValue.numbers.toMutableList()
                                    numbers[numbers.indexOf(number)] = newNumber
                                    setEditValue(editValue.copy(numbers = numbers))
                                }, isRemovable = true
                            ) { }
                        }
                    }
                }
            }

            BottomBar(onDismiss) {
                onSave(editValue)
                onDismiss()
            }
        }
    }
}