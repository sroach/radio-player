package gy.roach.radio

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import radio_guyana_player.composeapp.generated.resources.Res
import radio_guyana_player.composeapp.generated.resources.equalizer1

/**
 * Desktop implementation of the SplashScreen.
 *
 * @param onSplashFinished Callback to execute when the splash screen animation is complete
 */
@Composable
actual fun SplashScreen(onSplashFinished: () -> Unit) {
    // State to track if the splash screen animation is complete
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    // Start the animation when the composable is first composed
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000) // Show splash screen for 2 seconds
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        // App logo
        Image(
            painter = painterResource(Res.drawable.equalizer1),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(200.dp)
                .alpha(alphaAnim.value)
        )

        // App name text at the bottom
        Text(
            text = "Radio Guyana Player",
            color = MaterialTheme.colors.onPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
                .alpha(alphaAnim.value)
        )
    }
}