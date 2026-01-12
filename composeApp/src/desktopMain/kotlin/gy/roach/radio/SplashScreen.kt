package gy.roach.radio

import androidx.compose.animation.core.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gy.roach.radio.theme.GradientBackgrounds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gy.roach.radio.GuyanaColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Enhanced implementation of the SplashScreen following frontend_aesthetics rules.
 */
@Composable
actual fun SplashScreen(onSplashFinished: () -> Unit) {
    val titleAlpha = remember { Animatable(0f) }
    val titleOffsetY = remember { Animatable(20f) }
    val taglineAlpha = remember { Animatable(0f) }

    // Control for the equalizer "signal" reveal
    var startEqualizer by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // 1. Start the equalizer bars immediately
        startEqualizer = true

        // 2. Title slides up shortly after
        delay(600)
        launch {
            titleAlpha.animateTo(1f, tween(800))
        }
        launch {
            titleOffsetY.animateTo(0f, spring(Spring.DampingRatioLowBouncy))
        }

        // 3. Tagline fades in last
        delay(400)
        taglineAlpha.animateTo(1f, tween(1000))

        delay(1800) // Keep the vibe going
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientBackgrounds.goldenArrowheadDark()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // HIGH IMPACT MOMENT: Staggered animated equalizer bars
            AnimatedEqualizer(
                isVisible = startEqualizer,
                modifier = Modifier.height(120.dp).padding(horizontal = 40.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // App name with distinctive typography
            Text(
                text = "GY TUNES",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 4.sp
                ),
                color = GuyanaColors.FlagGold,
                modifier = Modifier
                    .graphicsLayer(translationY = titleOffsetY.value)
                    .alpha(titleAlpha.value)
            )

            // Stylized tagline reveal
            Text(
                text = "Your Sound of Home",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.sp
                ),
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .alpha(taglineAlpha.value)
            )
        }
    }
}

@Composable
private fun AnimatedEqualizer(isVisible: Boolean, modifier: Modifier = Modifier) {
    val barColors = listOf(
        GuyanaColors.FlagRed,
        GuyanaColors.FlagGold,
        GuyanaColors.FlagGreen,
        GuyanaColors.FlagGold,
        GuyanaColors.FlagRed
    )

    // Seeded random to keep bars consistent across recompositions but unique per bar
    val random = remember { Random(42) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        barColors.forEachIndexed { index, color ->
            val infiniteTransition = rememberInfiniteTransition(label = "bar$index")

            // Explicitly define types to avoid inference errors
            val duration: Int = remember(index) { random.nextInt(600, 1000) }
            val targetHeight: Float = remember(index) { random.nextDouble(0.4, 1.0).toFloat() }

            val heightScale by infiniteTransition.animateFloat(
                initialValue = 0.1f,
                targetValue = if (isVisible) targetHeight else 0.1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(duration, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "height$index"
            )

            Box(
                modifier = Modifier
                    .width(16.dp)
                    .fillMaxHeight(heightScale)
                    .background(
                        color = color,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(
                            topStart = 4.dp,
                            topEnd = 4.dp
                        )
                    )
                    .graphicsLayer {
                        // Atmosphere: Glow effect against the dark gradient
                        alpha = 0.9f
                        shadowElevation = 12f
                        spotShadowColor = color
                        ambientShadowColor = color
                    }
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    MaterialTheme {
        SplashScreen(onSplashFinished = {})
    }
}