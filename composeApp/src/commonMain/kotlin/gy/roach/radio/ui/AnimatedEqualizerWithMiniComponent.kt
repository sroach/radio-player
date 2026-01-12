package gy.roach.radio.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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

    Canvas(modifier = modifier) {
        val canvasWidth = this.size.width
        val canvasHeight = this.size.height

        val totalBarWidthPx = barWidth.toPx()
        val spacingPx = barSpacing.toPx()
        val totalBarsWidth = (barCount * totalBarWidthPx) + ((barCount - 1) * spacingPx)

        // 💡 Fix: Only center if there is actually enough room.
        // In small list items, we default to 0 (left-aligned).
        val startX = if (canvasWidth > totalBarsWidth) {
            (canvasWidth - totalBarsWidth) / 2f
        } else {
            0f
        }
        val maxHeight = canvasHeight * 0.8f
        val bottomY = canvasHeight * 0.9f

        // 🇬🇾 Full Flag Palette Strategy
        // We use Green (Jungle), Gold (Arrowhead), and Red (Zeal) for the main bars.
        // We'll cycle through them for a vibrant look.
        val flagColors = listOf(
            GuyanaColors.FlagGreen,
            GuyanaColors.FlagGold,
            GuyanaColors.FlagRed
        )

        for (i in 0 until barCount) {
            val heightFraction = if (isPlaying) barHeights[i].value else staticHeights.getOrElse(i % staticHeights.size) { 0.5f }
            val barHeight = maxHeight * heightFraction
            val x = startX + (i * (totalBarWidthPx + spacingPx))
            val y = bottomY - barHeight

            val barColor = flagColors[i % flagColors.size]

            // 1. Draw the "Black Border/Shadow" (Unity)
            // Drawing a slightly larger black bar underneath for a high-end outline look
            drawRoundRect(
                color = GuyanaColors.FlagBlack.copy(alpha = 0.4f),
                topLeft = Offset(x - 1.dp.toPx(), y - 1.dp.toPx()),
                size = androidx.compose.ui.geometry.Size(totalBarWidthPx + 2.dp.toPx(), barHeight + 2.dp.toPx()),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(totalBarWidthPx / 2f)
            )

            // 2. Draw the Main Flag Color Bar
            drawRoundRect(
                color = barColor,
                topLeft = Offset(x, y),
                size = androidx.compose.ui.geometry.Size(totalBarWidthPx, barHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(totalBarWidthPx / 2f)
            )

            // 3. Draw the "White Highlight" (Waters)
            // A tiny white dot at the top of the bar to make it look like sunlit water/glass
            if (barHeight > 10.dp.toPx()) {
                drawCircle(
                    color = Color.White.copy(alpha = 0.5f),
                    radius = totalBarWidthPx / 4f,
                    center = Offset(x + (totalBarWidthPx / 2f), y + (totalBarWidthPx / 2f))
                )
            }
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
        baseColor = tint
    )
}
