package gy.roach.radio

import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        // Create a theme state that will be shared with the App
        val themeState = remember { ThemeState() }

        // Sync the theme state with web CSS
        SyncThemeWithWebCss(themeState)

        // Use RadioGuyanaTheme with our theme state
        RadioGuyanaTheme(darkTheme = themeState.isDarkTheme) {
            App(themeState = themeState)
        }
    }
}
