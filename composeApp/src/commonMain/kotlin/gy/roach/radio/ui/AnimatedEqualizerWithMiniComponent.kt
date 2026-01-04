package gy.roach.radio.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gy.roach.radio.theme.GuyanaColors

/**
 * Animated equalizer bars that visualize audio playback.
 * Uses Guyanese flag colors for a cultural touch.
 */
@Composable
fun AnimatedEqualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    barCount: Int = 4,
    barWidth: Dp = 4.dp,
    barSpacing: Dp = 3.dp,
    size: Dp = 24.dp,
    baseColor: Color = GuyanaColors.FlagGreen
) {
    val infiniteTransition = rememberInfiniteTransition(label = "equalizer")
    
    // Create different animation phases for each bar
    val barHeights = List(barCount) { index ->
        val delay = index * 100 // Stagger the animations
        
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 400 + (index * 50), // Vary duration for organic feel
                    easing = EaseInOutSine,
                    delayMillis = delay
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "barHeight$index"
        )
    }

    // Static heights when not playing
    val staticHeights = listOf(0.6f, 0.4f, 0.8f, 0.5f)

    Canvas(modifier = modifier.size(size)) {
        val totalBarWidth = barWidth.toPx()
        val spacing = barSpacing.toPx()
        val totalWidth = (barCount * totalBarWidth) + ((barCount - 1) * spacing)
        val startX = (size.toPx() - totalWidth) / 2
        val maxHeight = size.toPx() * 0.8f
        val bottomY = size.toPx() * 0.9f

        // Color gradient based on bar position (Guyanese colors)
        val colors = listOf(
            GuyanaColors.FlagGreen,
            GuyanaColors.FlagGreen.copy(green = 0.7f),
            GuyanaColors.FlagGold,
            GuyanaColors.FlagGold.copy(red = 1f, green = 0.75f)
        )

        for (i in 0 until barCount) {
            val heightFraction = if (isPlaying) barHeights[i].value else staticHeights.getOrElse(i) { 0.5f }
            val barHeight = maxHeight * heightFraction
            val x = startX + (i * (totalBarWidth + spacing))
            val y = bottomY - barHeight
            
            val barColor = colors.getOrElse(i) { baseColor }

            drawRoundRect(
                color = barColor,
                topLeft = Offset(x, y),
                size = Size(totalBarWidth, barHeight),
                cornerRadius = CornerRadius(totalBarWidth / 2, totalBarWidth / 2)
            )
        }
    }
}

/**
 * Mini equalizer for use in compact spaces like list items.
 */
@Composable
fun MiniEqualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    tint: Color = GuyanaColors.FlagGreen
) {
    AnimatedEqualizer(
        isPlaying = isPlaying,
        modifier = modifier,
        barCount = 3,
        barWidth = 3.dp,
        barSpacing = 2.dp,
        size = 16.dp,
        baseColor = tint
    )
}
