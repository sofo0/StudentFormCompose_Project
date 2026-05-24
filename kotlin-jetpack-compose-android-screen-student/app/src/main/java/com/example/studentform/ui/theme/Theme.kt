package com.example.studentform.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val StudentColorScheme = darkColorScheme(
    primary = Coral,
    secondary = Mint,
    tertiary = Brass,
    background = Ink,
    surface = InkSoft,
    onPrimary = Paper,
    onSecondary = Ink,
    onTertiary = Ink,
    onBackground = Paper,
    onSurface = Paper
)

@Composable
fun StudentFormTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = StudentColorScheme,
        typography = MaterialTheme.typography.copy(
            headlineLarge = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 31.sp,
                lineHeight = 36.sp
            ),
            titleMedium = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                lineHeight = 20.sp
            ),
            bodyMedium = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                lineHeight = 21.sp
            )
        ),
        content = content
    )
}
