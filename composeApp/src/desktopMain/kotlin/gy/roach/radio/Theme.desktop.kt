package gy.roach.radio

import androidx.compose.runtime.Composable

/**
 * Desktop implementation of SystemAppearance.
 * This is a no-op implementation since desktop applications typically
 * don't need special handling for system appearance.
 */
@Composable
internal actual fun SystemAppearance(isDark: Boolean) {
    // No-op implementation for desktop
    // Desktop applications typically handle appearance through the window manager
    // or operating system settings
}