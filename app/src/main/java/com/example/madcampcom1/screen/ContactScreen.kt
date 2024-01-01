package com.example.madcampcom1.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madcampcom1.component.ContactGroupHeader
import com.example.madcampcom1.component.ContactItem
import com.example.madcampcom1.component.Menu
import com.example.madcampcom1.component.TopBar
import com.example.madcampcom1.ui.theme.Background
import com.example.madcampcom1.ui.theme.Border
import com.example.madcampcom1.viewModel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ContactScreen(
    contactViewModel: ContactViewModel
) {

    val uiState by contactViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopBar("My Contact", Background) {
                IconButton(onClick = { /*TODO*/ }) {
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
                    } to { /*TODO*/ }, @Composable {
                        Text(
                            text = "전체 삭제", fontSize = 16.sp, color = Color(0xFFDA0000)
                        )
                    } to { /*TODO*/ })
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
                                ContactItem(
                                    contactEntity = item,
                                    onClickItem = { contactViewModel.onItemClicked(item.id) },
                                    isExpanded = contactViewModel.isExpanded(item.id)
                                )
                                if (value.last() != item) Divider(
                                    modifier = Modifier.padding(horizontal = 20.dp), color = Border
                                )
                            }
                        }
                    }
                }
            })
    }
}