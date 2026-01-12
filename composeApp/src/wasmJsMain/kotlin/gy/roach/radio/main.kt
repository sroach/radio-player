package gy.roach.radio

import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import androidx.compose.ui.window.ComposeViewport
import gy.roach.radio.theme.ThemeSettings
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(content = {
            // Create the theme settings instance for Web
            val themeSettings = remember { ThemeSettings() }

            // Pass themeSettings instead of themeState
            App(themeSettings = themeSettings)
        })
}
