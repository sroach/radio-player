package gy.roach.radio.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Manages the current theme state of the application.
 * Follows iOS design patterns for theme management.
 */
class ModernThemeState {
    var selectedTheme by mutableStateOf(ColorTheme.MINIMALIST)
        private set
    
    var isDarkTheme by mutableStateOf(false)
        private set
    
    /**
     * Updates the selected color theme.
     */
    fun selectTheme(theme: ColorTheme) {
        selectedTheme = theme
    }
    
    /**
     * Toggles between light and dark mode.
     */
    fun toggleDarkMode() {
        isDarkTheme = !isDarkTheme
    }
    
    /**
     * Sets the dark mode state explicitly.
     */
    fun setDarkMode(isDark: Boolean) {
        isDarkTheme = isDark
    }
}
