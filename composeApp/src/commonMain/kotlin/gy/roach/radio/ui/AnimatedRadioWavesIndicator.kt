package gy.roach.radio.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gy.roach.radio.theme.GuyanaColors

/**
 * Animated radio waves that pulse outward when playing.
 */
@Composable
fun AnimatedRadioWaves(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    waveCount: Int = 3,
    baseColor: Color = GuyanaColors.FlagGold
) {
    val infiniteTransition = rememberInfiniteTransition(label = "radioWaves")

    // Create staggered wave animations
    val waveAnimations = List(waveCount) { index ->
        val delay = index * 400 // Stagger waves

        val scale by infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1200,
                    easing = EaseOut,
                    delayMillis = delay
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "waveScale$index"
        )

        val alpha by infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1200,
                    easing = EaseOut,
                    delayMillis = delay
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "waveAlpha$index"
        )

        Pair(scale, alpha)
    }

    Canvas(modifier = modifier.size(size)) {
        val center = this.center
        val maxRadius = size.toPx() / 2

        if (isPlaying) {
            // Draw animated waves
            waveAnimations.forEach { (scale, alpha) ->
                val radius = maxRadius * scale
                drawCircle(
                    color = baseColor.copy(alpha = alpha),
                    radius = radius,
                    center = center,
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }

        // Draw center dot (always visible)
        drawCircle(
            color = if (isPlaying) GuyanaColors.FlagGreen else baseColor.copy(alpha = 0.5f),
            radius = maxRadius * 0.15f,
            center = center
        )
    }
}

/**
 * Compact radio waves indicator for bottom bar.
 */
@Composable
fun CompactRadioWaves(
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedRadioWaves(
        isPlaying = isPlaying,
        modifier = modifier,
        size = 32.dp,
        waveCount = 2,
        baseColor = GuyanaColors.FlagGold
    )
}
