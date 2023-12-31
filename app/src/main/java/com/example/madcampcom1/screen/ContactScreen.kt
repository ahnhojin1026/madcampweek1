package com.example.madcampcom1.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madcampcom1.component.ContactEditDialog
import com.example.madcampcom1.component.ContactGroupHeader
import com.example.madcampcom1.component.ContactItem
import com.example.madcampcom1.component.ContactMsgDialog
import com.example.madcampcom1.component.MainTopBar
import com.example.madcampcom1.component.Menu
import com.example.madcampcom1.data.local.entity.ContactEntity
import com.example.madcampcom1.ui.theme.Background
import com.example.madcampcom1.ui.theme.Border
import com.example.madcampcom1.ui.theme.Red
import com.example.madcampcom1.ui.theme.Surface
import com.example.madcampcom1.viewModel.ContactViewModel

@Composable
fun ContactScreen(
    contactViewModel: ContactViewModel, onNavigateToDetail: () -> Unit
) {

    val uiState by contactViewModel.uiState.collectAsState()
    val dialogValue = remember { mutableStateOf<ContactEntity?>(null) }
    val isEditDialogOpened = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MainTopBar("My Contact", Background) {
                IconButton(onClick = {
                    contactViewModel.setDetailValue(
                        ContactEntity(
                            name = "", numbers = listOf("")
                        )
                    )
                    isEditDialogOpened.value = true
                }) {
                    Icon(Icons.Rounded.Add, "add")
                }
                IconButton(onClick = { contactViewModel.onMenu(true) }) {
                    Icon(Icons.Rounded.MoreVert, "more_vert")
                }

                Menu(
                    isMenuExpanded = uiState.isMenuExpanded,
                    close = { contactViewModel.onMenu(false) },
                    menuItems = linkedMapOf(@Composable {
                        Text(
                            text = "연락처 불러오기", fontSize = 14.sp
                        )
                    } to { contactViewModel.getContactFromContentResolver() }, @Composable {
                        Text(
                            text = "전체 삭제", fontSize = 14.sp, color = Red
                        )
                    } to { contactViewModel.removeAll() })
                )
            }
        }, containerColor = Background
    ) {
        LazyColumn(modifier = Modifier
            .padding(it)
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
            contentPadding = PaddingValues(top = 10.dp, bottom = 40.dp),
            content = {
                uiState.contactMap.forEach { (key, value) ->
                    item {
                        ContactGroupHeader(key = key.toString())
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .padding(bottom = 4.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .border(
                                    width = 1.dp, color = Border, shape = RoundedCornerShape(24.dp)
                                )
                                .background(Surface)
                        ) {
                            value.forEach { item ->
                                ContactItem(contactEntity = item,
                                    onClickItem = { contactViewModel.onItemClicked(item.id) },
                                    isExpanded = contactViewModel.isExpanded(item.id),
                                    onInfo = {
                                        contactViewModel.setDetailValue(item)
                                        onNavigateToDetail()
                                    },
                                    onDelete = { dialogValue.value = item })
                                if (value.last() != item) Divider(
                                    modifier = Modifier.padding(horizontal = 24.dp), color = Border
                                )
                            }
                        }
                    }
                }
            })

        if (dialogValue.value != null) ContactMsgDialog("연락처를 삭제할까요?", onDismissRequest = {
            dialogValue.value = null
        }, confirmText = "삭제") {
            contactViewModel.removeContact(dialogValue.value!!)
        }

        if (isEditDialogOpened.value) ContactEditDialog(uiState.detailValue!!,
            { v -> contactViewModel.setDetailValue(v) },
            { isEditDialogOpened.value = false }) { v -> contactViewModel.addContact(v) }
    }
}

/*@Composable
fun ContactDialog(
    setDialogValue: (ContactEntity?) -> Unit,
    dialogValue: ContactEntity,
    onAdd: (ContactEntity) -> Unit,
    onUpdate: (ContactEntity) -> Unit,
) {
    val isAdd = dialogValue.id == 0
    val actionName = if (isAdd) "추가" else "수정"

    Dialog(onDismissRequest = { setDialogValue(null) }) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(Background),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    "연락처 $actionName", fontSize = 20.sp, fontWeight = FontWeight.Bold
                )

                Column(modifier = Modifier.padding(vertical = 20.dp)) {
                    OutlinedTextField(dialogValue.name,
                        onValueChange = { name ->
                            setDialogValue(dialogValue.copy(name = name))
                        },
                        label = { Text("이름") },
                        leadingIcon = { Icon(Icons.Default.Person, "") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    Box(modifier = Modifier.height(12.dp))/*OutlinedTextField(
                        dialogValue.number,
                        onValueChange = { number ->
                            setDialogValue(dialogValue.copy(number = number))
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                        label = { Text("전화번호") },
                        leadingIcon = { Icon(Icons.Rounded.Phone, "") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )*/
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    FilledTonalButton(
                        onClick = { setDialogValue(null) },
                        modifier = Modifier.weight(1F),
                    ) {
                        Text("취소", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }

                    Box(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = {
                            if (isAdd) onAdd(dialogValue) else onUpdate(dialogValue)
                            setDialogValue(null)
                        }, modifier = Modifier.weight(1F)
                    ) {
                        Text(actionName, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}*/

/*@Preview
@Composable
fun PreviewContactDialog() {
    ContactDialog(setDialogValue = { },
        dialogValue = ContactEntity(name = "", number = ""),
        { },
        { })
}*/