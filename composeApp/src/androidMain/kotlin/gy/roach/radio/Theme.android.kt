package gy.roach.radio

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Android implementation of SystemAppearance.
 * This implementation adjusts the system UI appearance based on the theme.
 */
@Composable
internal actual fun SystemAppearance(isDark: Boolean) {
    // For Android, we could adjust the system bars color here if needed
    // This is a minimal implementation that doesn't change system appearance
    // A more complete implementation would use WindowCompat to adjust system bars
}