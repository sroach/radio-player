package gy.roach.radio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window

/**
 * Synchronizes the Compose theme state with the web CSS theme by updating the HTML body class.
 * Also handles theme persistence using localStorage.
 */
@Composable
fun SyncThemeWithWebCss(themeState: ThemeState) {
    // Initial setup - load saved theme preference
    LaunchedEffect(Unit) {
        // Try to load saved theme preference from localStorage
        try {
            val savedTheme = localStorage.getItem("isDarkTheme")
            if (savedTheme != null) {
                themeState.isDarkTheme = savedTheme.toBoolean()
            }
        } catch (e: Exception) {
            window.alert("Failed to load theme preference: ${e.message}")
        }
    }

    // Update body class and save preference when theme changes
    DisposableEffect(themeState.isDarkTheme) {
        // Update body class based on current theme
        val body = document.body
        if (body != null) {
            if (themeState.isDarkTheme) {
                body.classList.remove("light-theme")
                body.classList.add("dark-theme")
            } else {
                body.classList.remove("dark-theme")
                body.classList.add("light-theme")
            }
        }

        // Save theme preference to localStorage
        try {
            localStorage.setItem("isDarkTheme", themeState.isDarkTheme.toString())
        } catch (e: Exception) {
            window.alert("Failed to save theme preference: ${e.message}")
        }

        onDispose { }
    }
}
