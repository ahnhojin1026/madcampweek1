package com.example.madcampcom1.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class MyRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color =
        RippleTheme.defaultRippleColor(Color.Black, lightTheme = !isSystemInDarkTheme())

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        Color.Black, lightTheme = !isSystemInDarkTheme()
    )
}