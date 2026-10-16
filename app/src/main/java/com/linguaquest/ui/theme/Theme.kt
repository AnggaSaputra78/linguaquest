package com.linguaquest.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    primaryContainer = GreenPrimaryLight,
    onPrimaryContainer = Color(0xFF052014),
    secondary = BlueAccent,
    onSecondary = Color.White,
    tertiary = YellowAccent,
    onTertiary = Color(0xFF3A2E00),
    background = LightBackground,
    onBackground = Color(0xFF161C19),
    surface = LightSurface,
    onSurface = Color(0xFF161C19)
)

private val DarkColors = darkColorScheme(
    primary = GreenPrimary,
    onPrimary = Color(0xFF042214),
    primaryContainer = GreenPrimaryDark,
    onPrimaryContainer = Color(0xFFD9F8E7),
    secondary = BlueAccent,
    onSecondary = Color(0xFF04203C),
    tertiary = YellowAccent,
    onTertiary = Color(0xFF3A2E00),
    background = DarkBackground,
    onBackground = Color(0xFFE1E8E4),
    surface = DarkSurface,
    onSurface = Color(0xFFE1E8E4)
)

@Composable
fun LinguaQuestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = LinguaQuestTypography,
        content = content
    )
}