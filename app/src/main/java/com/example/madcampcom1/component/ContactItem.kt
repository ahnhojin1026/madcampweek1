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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madcampcom1.R
import com.example.madcampcom1.data.local.entity.ContactEntity
import com.example.madcampcom1.ui.theme.Green
import com.example.madcampcom1.ui.theme.Grey500
import com.example.madcampcom1.ui.theme.Red

@Composable
fun ContactItem(
    contactEntity: ContactEntity,
    onClickItem: () -> Unit,
    isExpanded: Boolean,
    onInfo: () -> Unit,
    onDelete: () -> Unit
) {
    Column {
        ItemHeader(name = contactEntity.name, onClickItem = onClickItem)
        ItemBody(
            number = contactEntity.defaultNumber(),
            isExpanded = isExpanded,
            onInfo = onInfo,
            onDelete = onDelete
        )
    }
}

@Composable
fun ItemHeader(name: String, onClickItem: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClickItem)
            .padding(horizontal = 24.dp, vertical = 12.dp), contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = name,
            fontSize = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun ItemBody(number: String, isExpanded: Boolean, onInfo: () -> Unit, onDelete: () -> Unit) {
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 60.dp, vertical = 8.dp),
                text = number,
                color = Green,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                IconButton(
                    onClick = onInfo, modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_info), "info", tint = Grey500
                    )
                }

                IconButton(
                    onClick = onDelete, modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        Icons.Rounded.Delete, "delete", tint = Red
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
        name = "한줄이름", numbers = listOf(
            "010-1234-5678", "010-1234-5678"
        )
    ), onClickItem = { }, isExpanded = true, onInfo = { }, onDelete = { })
}