package com.example.madcampcom1.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.madcampcom1.R
import com.example.madcampcom1.ui.theme.Grey500
import com.example.madcampcom1.ui.theme.Red

@Composable
fun ContactFieldItem(
    value: String,
    onValueChange: (String) -> Unit,
    isRemovable: Boolean,
    onRemove: () -> Unit
) {
    Row(verticalAlignment = Alignment.Bottom) {
        BasicTextField(
            modifier = Modifier
                .weight(1F)
                .height(37.dp)
                .padding(start = 36.dp),
            value = value,
            onValueChange = onValueChange,
            singleLine = true
        ) { innerTextField ->
            Column {
                Row(
                    modifier = Modifier.weight(1F), verticalAlignment = Alignment.CenterVertically
                ) {
                    innerTextField()
                }
                Divider(color = Grey500)
            }

        }

        if (isRemovable) IconButton(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(32.dp), onClick = onRemove
        ) {
            Icon(painterResource(id = R.drawable.ic_remove), "remove", tint = Red)
        }
    }
}

@Preview
@Composable
fun PreviewContactFieldItem() {
    ContactFieldItem("010-1234-5678", { }, true) { }
}
