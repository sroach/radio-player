package gy.roach.radio

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gy.roach.radio.theme.ColorTheme
import gy.roach.radio.theme.GradientBackgrounds
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import radio_guyana_player.composeapp.generated.resources.Res
import radio_guyana_player.composeapp.generated.resources.equalizer1

/**
 * Android implementation of the SplashScreen with atmospheric gradients.
 */
@Composable
actual fun SplashScreen(onSplashFinished: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }

    // Fade-in animation
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800, easing = EaseOutCubic),
        label = "alpha"
    )

    // Scale animation for the logo - subtle bounce effect
    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    // Pulsing glow effect
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2500) // Show splash screen for 2.5 seconds
        onSplashFinished()
    }

    // Get theme colors - using Golden Arrowhead as the default splash theme
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val backgroundGradient = GradientBackgrounds.getGradient(ColorTheme.GOLDEN_ARROWHEAD, isDark)
    val accentColor = ColorTheme.GOLDEN_ARROWHEAD.accentColor

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        // Radial glow behind the logo
        Box(
            modifier = Modifier
                .size(350.dp)
                .alpha(glowAlpha * alphaAnim.value)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            accentColor.copy(alpha = 0.4f),
                            accentColor.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                )
        )

        // App logo with scale animation
        Image(
            painter = painterResource(Res.drawable.equalizer1),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(200.dp)
                .scale(scaleAnim.value)
                .alpha(alphaAnim.value)
        )

        // App name text at the bottom
        Text(
            text = "GY Tunes",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
                .alpha(alphaAnim.value)
        )

        // Tagline
        Text(
            text = "Your Sound of Home",
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
                .alpha(alphaAnim.value)
        )
    }
}

// Helper extension for luminance check
private fun androidx.compose.ui.graphics.Color.luminance(): Float {
    return (0.299f * red + 0.587f * green + 0.114f * blue)
}