package com.example.madcampcom1.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madcampcom1.ui.theme.Blue
import com.example.madcampcom1.ui.theme.Border
import com.example.madcampcom1.ui.theme.Surface

@Composable
fun ContactNumberList(numbers: List<String>, onItemLongClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .border(
                width = 1.dp, color = Border, shape = RoundedCornerShape(24.dp)
            )
            .background(Surface)
    ) {
        numbers.forEachIndexed { index, number ->
            ContactNumberItem(
                number = number, isDefault = index == 0, onItemLongClick = onItemLongClick
            )

            if (index != numbers.size - 1) Divider(
                modifier = Modifier.padding(horizontal = 24.dp), color = Border
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ContactNumberItem(
    number: String, isDefault: Boolean, onItemLongClick: (String) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .combinedClickable(onLongClick = { onItemLongClick(number) }) {}
        .padding(horizontal = 24.dp, vertical = 12.dp)) {
        if (isDefault) Column {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "기본 번호",
                fontSize = 12.sp,
                color = Blue
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1F),
                    text = number,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
                Icon(Icons.Rounded.Check, "check", tint = Blue)
            }
        } else Text(
            modifier = Modifier.padding(end = 48.dp),
            text = number,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
    }
}

@Preview
@Composable
fun PreviewContactNumberList() {
    ContactNumberList(numbers = listOf("010-1234-5678", "010-1234-5678")) {}
}