package com.example.madcampcom1.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.madcampcom1.component.ContactGroupHeader
import com.example.madcampcom1.component.ContactItem
import com.example.madcampcom1.component.Menu
import com.example.madcampcom1.component.TopBar
import com.example.madcampcom1.data.local.entity.ContactEntity
import com.example.madcampcom1.ui.theme.Background
import com.example.madcampcom1.ui.theme.Border
import com.example.madcampcom1.viewModel.ContactViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactScreen(
    contactViewModel: ContactViewModel,
    onNavigateToDetail: (Int) -> Unit
) {

    val uiState by contactViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopBar("My Contact", Background) {
                IconButton(onClick = {
                    contactViewModel.setDialogValue(
                        ContactEntity(
                            name = "", numbers = listOf("")
                        )
                    )
                }) {
                    Icon(Icons.Default.Add, "")
                }
                IconButton(onClick = { contactViewModel.onMenu(true) }) {
                    Icon(Icons.Default.MoreVert, "")
                }

                Menu(
                    isMenuExpanded = uiState.isMenuExpanded,
                    close = { contactViewModel.onMenu(false) },
                    menuItems = linkedMapOf(@Composable {
                        Text(
                            text = "연락처 가져오기", fontSize = 16.sp
                        )
                    } to { contactViewModel.getContactFromContentResolver() }, @Composable {
                        Text(
                            text = "전체 삭제", fontSize = 16.sp, color = Color(0xFFDA0000)
                        )
                    } to { contactViewModel.removeAll() })
                )
            }
        }, containerColor = Background
    ) {
        LazyColumn(modifier = Modifier.padding(it),
            contentPadding = PaddingValues(bottom = 40.dp),
            content = {
                uiState.contactMap.forEach { (key, value) ->
                    stickyHeader {
                        ContactGroupHeader(key = key.toString())
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .border(
                                    width = 1.dp, color = Border, shape = RoundedCornerShape(20.dp)
                                )
                                .background(Color.White)
                        ) {
                            value.forEach { item ->
                                ContactItem(contactEntity = item,
                                    onClickItem = { onNavigateToDetail(item.id) }/*{ contactViewModel.onItemClicked(item.id) }*/,
                                    isExpanded = contactViewModel.isExpanded(item.id),
                                    onEdit = { contactViewModel.setDialogValue(item) },
                                    onDelete = { contactViewModel.removeContact(item) })
                                if (value.last() != item) Divider(
                                    modifier = Modifier.padding(horizontal = 20.dp), color = Border
                                )
                            }
                        }
                    }
                }
            })

        if (uiState.dialogValue != null) ContactDialog({ v -> contactViewModel.setDialogValue(v) },
            uiState.dialogValue!!,
            { v -> contactViewModel.addContact(v) },
            { v -> contactViewModel.updateContact(v) })
    }
}

@Composable
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
                    Box(modifier = Modifier.height(12.dp))
                    /*OutlinedTextField(
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
}

/*@Preview
@Composable
fun PreviewContactDialog() {
    ContactDialog(setDialogValue = { },
        dialogValue = ContactEntity(name = "", number = ""),
        { },
        { })
}*/