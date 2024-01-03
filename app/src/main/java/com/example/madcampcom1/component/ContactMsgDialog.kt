package com.example.madcampcom1.component

import android.view.Gravity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.example.madcampcom1.ui.theme.Grey800
import com.example.madcampcom1.ui.theme.MyRippleTheme
import com.example.madcampcom1.ui.theme.Orange
import com.example.madcampcom1.ui.theme.Surface

@Composable
fun ContactMsgDialog(
    message: String, onDismissRequest: () -> Unit, confirmText: String, onConfirm: () -> Unit
) {
    CompositionLocalProvider(LocalRippleTheme provides MyRippleTheme()) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
            dialogWindowProvider.window.setGravity(Gravity.BOTTOM)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(Surface),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 6.dp),
                        text = message,
                        color = Grey800,
                        fontSize = 14.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DialogButton(text = "취소", onDismissRequest)

                        Divider(modifier = Modifier.size(1.dp, 16.dp))

                        DialogButton(text = confirmText) {
                            onConfirm()
                            onDismissRequest()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.DialogButton(text: String, onClick: () -> Unit) {
    TextButton(
        modifier = Modifier
            .weight(1F)
            .height(30.dp)
            .padding(horizontal = 4.dp),
        contentPadding = PaddingValues(),
        onClick = onClick
    ) {
        Text(text, color = Orange, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun PreviewContactMsgDialog() {
    ContactMsgDialog(message = "다이얼로그 메시지", onDismissRequest = {}, "확인") { }
}