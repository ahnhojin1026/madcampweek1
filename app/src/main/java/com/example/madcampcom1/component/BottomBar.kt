package com.example.madcampcom1.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madcampcom1.ui.theme.MyRippleTheme
import com.example.madcampcom1.ui.theme.Orange

@Composable
fun BottomBar(onDismiss: () -> Unit, onSave: () -> Unit) {
    CompositionLocalProvider(LocalRippleTheme provides MyRippleTheme()) {
        Row(
            modifier = Modifier
                .height(56.dp)
                .padding(horizontal = 24.dp, vertical = 4.dp)
        ) {
            BottomBarButton(text = "취소", onDismiss)
            BottomBarButton(text = "저장", onSave)
        }
    }
}

@Composable
private fun RowScope.BottomBarButton(text: String, onClick: () -> Unit) {
    TextButton(
        modifier = Modifier
            .weight(1F)
            .height(48.dp), onClick = onClick
    ) {
        Text(text, color = Orange, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun PreviewBottomBar() {
    BottomBar({ }) { }
}