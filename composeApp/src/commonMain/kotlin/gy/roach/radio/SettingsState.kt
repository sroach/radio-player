package gy.roach.radio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * Class to manage settings state in the app.
 * Currently a placeholder for future settings functionality.
 */
class SettingsState {
    // Future settings properties can be added here
}

/**
 * Remember a settings state that can be used for settings management.
 */
@Composable
fun rememberSettingsState(): SettingsState {
    return remember { SettingsState() }
}