package gy.roach.radio.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import gy.roach.radio.AppTypography
import gy.roach.radio.LocalThemeState
import gy.roach.radio.ThemeState


// Proxy theme wrapper only. The actual minimalist theme is defined in MinimalistRadioTheme.kt

// Add this import at the top


// Update the RadioGuyanaTheme function
@Composable
fun RadioGuyanaTheme(
    themeState: ModernThemeState = remember { ModernThemeState() },
    content: @Composable () -> Unit
) {
    val colorScheme = DynamicColorSchemeProvider.getColorScheme(
        theme = themeState.selectedTheme,
        isDark = themeState.isDarkTheme
    )

    CompositionLocalProvider(
        LocalThemeState provides ThemeState().apply {
            isDarkTheme = themeState.isDarkTheme
        }
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography, // This should work now since AppTypography is defined in this file
            content = content
        )
    }
}

