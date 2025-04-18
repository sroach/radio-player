package gy.roach.radio

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Splash screen that displays the app logo and transitions to the main screen after a delay.
 *
 * @param onSplashFinished Callback to execute when the splash screen animation is complete
 */
@Preview()
@Composable
expect fun SplashScreen(onSplashFinished: () -> Unit)
