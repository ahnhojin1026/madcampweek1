package com.example.madcampcom1.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madcampcom1.ui.theme.Background
import com.example.madcampcom1.ui.theme.Grey700

@Composable
fun ContactGroupHeader(key: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .background(Background)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = key, color = Grey700, fontSize = 12.sp)
    }
}

@Preview
@Composable
fun PreviewContactGroupHeader() {
    ContactGroupHeader(key = "o")
}