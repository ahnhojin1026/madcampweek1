package com.example.madcampcom1.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madcampcom1.data.local.entity.ContactEntity
import com.example.madcampcom1.viewModel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    contactViewModel: ContactViewModel
) {

    val uiState by contactViewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Add, "")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            content = {
                itemsIndexed(uiState.list) { _, item ->
                    ContactItem(
                        contactEntity = item,
                        onClickItem = { contactViewModel.onItemClicked(item.id) },
                        isExpanded = contactViewModel.isExpanded(item.id)
                    )
                }
            }
        )
    }
}

@Composable
fun ContactItem(contactEntity: ContactEntity, onClickItem: () -> Unit, isExpanded: Boolean) {
    Column {
        ItemHeader(name = contactEntity.name, onClickItem = onClickItem)
        ItemBody(number = contactEntity.number, isExpanded = isExpanded)
        Divider()
    }
}

@Composable
fun ItemHeader(name: String, onClickItem: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClickItem)
            .padding(8.dp)
    ) {
        Text(text = name, fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ItemBody(number: String, isExpanded: Boolean) {
    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top, animationSpec = tween(300)
        ) + fadeIn(
            animationSpec = tween(300)
        )
    }

    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top, animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        )
    }

    AnimatedVisibility(
        visible = isExpanded, enter = expandTransition, exit = collapseTransition
    ) {
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = number, fontSize = 16.sp, modifier = Modifier.fillMaxWidth()
            )
        }
    }
}