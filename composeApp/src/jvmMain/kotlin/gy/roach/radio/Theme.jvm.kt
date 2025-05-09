package gy.roach.radio

import androidx.compose.runtime.Composable

/**
 * JVM implementation of SystemAppearance.
 * This is a no-op implementation since JVM applications typically
 * don't need special handling for system appearance.
 */
@Composable
internal actual fun SystemAppearance(isDark: Boolean) {
    // No-op implementation for JVM
}