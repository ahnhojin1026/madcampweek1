package com.example.madcampcom1.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madcampcom1.ui.theme.Grey500

@Composable
fun ContactGroupHeader(key: String) {
    Box(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(text = key, color = Grey500, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun PreviewContactGroupHeader() {
    ContactGroupHeader(key = "o")
}