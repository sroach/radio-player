package gy.roach.radio.theme

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeSettings(private val settings: Settings = Settings()) {
    private val themeKey = "is_dark_theme"
    private val colorThemeKey = "color_theme"

    // Dark Mode
    private val _isDarkTheme = MutableStateFlow<Boolean?>(settings[themeKey])
    val isDarkTheme: StateFlow<Boolean?> = _isDarkTheme.asStateFlow()

    // Color Theme (stored as the enum name string)
    private val _colorTheme = MutableStateFlow<String?>(settings[colorThemeKey])
    val colorTheme: StateFlow<String?> = _colorTheme.asStateFlow()

    fun setTheme(isDark: Boolean?) {
        if (isDark == null) {
            settings.remove(themeKey)
        } else {
            settings[themeKey] = isDark
        }
        _isDarkTheme.value = isDark
    }

    fun setColorTheme(themeName: String?) {
        if (themeName == null) {
            settings.remove(colorThemeKey)
        } else {
            settings[colorThemeKey] = themeName
        }
        _colorTheme.value = themeName
    }
}
