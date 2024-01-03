package com.example.madcampcom1.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madcampcom1.ui.theme.Border
import com.example.madcampcom1.ui.theme.Grey500
import com.example.madcampcom1.ui.theme.Surface

@Composable
fun ContactDetailInfo(name: String, defaultNumber: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(
                width = 1.dp, color = Border, shape = RoundedCornerShape(24.dp)
            )
            .background(Surface)
            .padding(horizontal = 60.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 60.dp, vertical = 16.dp),
            text = name,
            fontSize = 22.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = defaultNumber,
            color = Grey500,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun PreviewContactDetailInfo() {
    ContactDetailInfo(name = "한줄이름", defaultNumber = "0123456789")
}