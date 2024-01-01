package com.example.madcampcom1.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madcampcom1.data.local.entity.ContactEntity

@Composable
fun ContactItem(
    contactEntity: ContactEntity,
    onClickItem: () -> Unit,
    isExpanded: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Column {
        ItemHeader(name = contactEntity.name, onClickItem = onClickItem)
        ItemBody(
            number = contactEntity.number,
            isExpanded = isExpanded,
            onEdit = onEdit,
            onDelete = onDelete
        )
    }
}

@Composable
fun ItemHeader(name: String, onClickItem: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClickItem)
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(text = name, fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ItemBody(number: String, isExpanded: Boolean, onEdit: () -> Unit, onDelete: () -> Unit) {
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = number, fontSize = 16.sp, color = Color(0xFF009900)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = onEdit)
                ) {
                    Icon(
                        Icons.Default.Edit, "", modifier = Modifier.padding(8.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = onDelete)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        "",
                        modifier = Modifier.padding(8.dp),
                        tint = Color(0xFFDA0000)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewContactItem() {
    ContactItem(contactEntity = ContactEntity(
        name = "이름", number = "010-1234-5678"
    ), onClickItem = { }, isExpanded = true, onEdit = { }, onDelete = { })
}