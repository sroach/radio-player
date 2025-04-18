package gy.roach.radio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * Sealed class representing the different screens in the app.
 */
sealed class Screen {
    /**
     * The splash screen shown when the app starts.
     */
    object Splash : Screen()

    /**
     * The main screen showing the radio stations.
     */
    object Main : Screen()

    /**
     * The about screen showing information about the app.
     */
    object About : Screen()

    /**
     * The settings screen for configuring app preferences.
     */
    object Settings : Screen()

    /**
     * The release notes screen showing the latest features and updates.
     */
    object ReleaseNotes : Screen()
}

/**
 * Class to manage navigation state in the app.
 */
class NavigationState {
    /**
     * The current screen being displayed.
     */
    var currentScreen by mutableStateOf<Screen>(Screen.Splash)
        private set

    /**
     * Navigate to the main screen.
     */
    fun navigateToMain() {
        currentScreen = Screen.Main
    }

    /**
     * Navigate to the about screen.
     */
    fun navigateToAbout() {
        currentScreen = Screen.About
    }

    /**
     * Navigate to the settings screen.
     */
    fun navigateToSettings() {
        currentScreen = Screen.Settings
    }

    /**
     * Navigate to the release notes screen.
     */
    fun navigateToReleaseNotes() {
        currentScreen = Screen.ReleaseNotes
    }
}

/**
 * Remember a navigation state that can be used for navigation.
 */
@Composable
fun rememberNavigationState(): NavigationState {
    return remember { NavigationState() }
}
