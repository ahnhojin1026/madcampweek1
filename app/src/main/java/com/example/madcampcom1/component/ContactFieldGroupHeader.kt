package com.example.madcampcom1.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madcampcom1.ui.theme.Green
import com.example.madcampcom1.ui.theme.Grey500
import com.example.madcampcom1.ui.theme.Grey600

@Composable
fun ContactFieldGroupHeader(isList: Boolean, onAdd: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isList) Icon(Icons.Rounded.Phone, "phone", tint = Grey500)
            else Icon(Icons.Rounded.AccountCircle, "account_circle", tint = Grey500)

            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = if (isList) "전화번호" else "이름",
                color = Grey600,
                fontSize = 18.sp,
            )
        }

        if (isList) IconButton(
            modifier = Modifier.size(32.dp), onClick = onAdd
        ) {
            Icon(Icons.Rounded.Add, "add", tint = Green)
        }
    }
}

@Preview
@Composable
fun PreviewContactFieldGroupHeader() {
    ContactFieldGroupHeader(true, { })
}