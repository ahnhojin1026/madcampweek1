package com.example.madcampcom1.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.madcampcom1.ui.theme.Surface

@Composable
fun Menu(
    isMenuExpanded: Boolean, close: () -> Unit, menuItems: Map<@Composable () -> Unit, () -> Unit>
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(24.dp))) {
        DropdownMenu(
            modifier = Modifier.background(Surface),
            expanded = isMenuExpanded,
            onDismissRequest = close
        ) {

            menuItems.forEach { (text, onClick) ->
                DropdownMenuItem(contentPadding = PaddingValues(horizontal = 24.dp),
                    text = text,
                    onClick = {
                        onClick()
                        close()
                    })
            }
        }
    }
}

@Preview
@Composable
fun PreviewMenu() {
    Menu(true,
        {},
        mapOf(@Composable { Text("menuItem1") } to {}, @Composable { Text("menuItem2") } to {})
    )
}