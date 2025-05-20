package gy.roach.radio

import androidx.compose.runtime.Composable
import kotlinx.browser.document

/**
 * WebAssembly JavaScript implementation of SystemAppearance.
 * This implementation adjusts the document's color scheme based on the theme.
 */
@Composable
internal actual fun SystemAppearance(isDark: Boolean) {
    // For web, we can set the color-scheme CSS property on the document
    // This helps with native UI elements like scrollbars
    val colorScheme = if (isDark) "dark" else "light"
    document.documentElement?.setAttribute("style", "color-scheme: $colorScheme")
}