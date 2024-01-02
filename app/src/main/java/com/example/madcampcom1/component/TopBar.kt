package com.example.madcampcom1.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.madcampcom1.ui.theme.Background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    color: Color?,
    actions: @Composable RowScope.() -> Unit,
) {
    TopAppBar(title = { Text(title, fontWeight = FontWeight.ExtraBold, fontSize = 30.sp) },
        colors = color?.let { TopAppBarDefaults.smallTopAppBarColors(it) }
            ?: TopAppBarDefaults.smallTopAppBarColors(),
        actions = actions)
}

@Preview
@Composable
fun PreviewTobAppBar() {
    TopBar("My Title", Background) {
        IconButton(onClick = { }) {
            Icon(Icons.Default.Add, "")
        }
    }
}