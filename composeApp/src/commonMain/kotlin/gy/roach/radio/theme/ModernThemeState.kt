package gy.roach.radio.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Manages the current theme state of the application.
 * Follows iOS design patterns for theme management.
 */
class ModernThemeState(
    private val themeSettings: ThemeSettings,
    initialIsDark: Boolean,
    initialTheme: ColorTheme = ColorTheme.GOLDEN_ARROWHEAD
) {
    var selectedTheme by mutableStateOf(initialTheme)
        private set

    var isDarkTheme by mutableStateOf(initialIsDark)
        private set

    fun selectTheme(theme: ColorTheme) {
        selectedTheme = theme
        themeSettings.setColorTheme(theme.name)
    }

    fun toggleDarkMode() {
        val newMode = !isDarkTheme
        isDarkTheme = newMode
        themeSettings.setTheme(newMode) // Now synchronous - no coroutine needed
    }

    fun setDarkMode(isDark: Boolean) {
        isDarkTheme = isDark
        themeSettings.setTheme(isDark)
    }
}
