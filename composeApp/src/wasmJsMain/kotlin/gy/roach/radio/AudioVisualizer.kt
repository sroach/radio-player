package gy.roach.radio

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlinx.coroutines.delay
import kotlin.math.sin
import kotlin.random.Random

/**
 * Actual implementation of AudioSpectrum for WASM/JS platform.
 * Displays a simulated audio spectrum visualization.
 * 
 * @param isPlaying Whether audio is currently playing
 * @param modifier Modifier for the Canvas
 */
@Composable
actual fun AudioSpectrum(
    isPlaying: Boolean,
    modifier: Modifier
) {
    // Number of bars in the equalizer
    val barCount = 32

    // State for bar heights
    val barHeights = remember { mutableStateOf(FloatArray(barCount) { 0.1f }) }

    // Animation state
    val animationState = remember { mutableStateOf(0f) }

    // Update animation when playing
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            // Continuously update the animation while playing
            while (isPlaying) {
                // Update animation state
                animationState.value += 0.05f

                // Generate new bar heights based on animation state
                val newHeights = FloatArray(barCount)
                for (i in 0 until barCount) {
                    // Create a wave-like pattern with some randomness
                    val baseHeight = sin((i * 0.2f + animationState.value) * 0.5f) * 0.3f + 0.5f
                    val randomFactor = Random.nextFloat() * 0.2f
                    newHeights[i] = (baseHeight + randomFactor).coerceIn(0.05f, 0.95f)
                }
                barHeights.value = newHeights

                // Delay for animation frame
                delay(50)
            }
        } else {
            // When not playing, gradually reduce bar heights to near zero
            var heights = barHeights.value
            while (heights.any { it > 0.1f }) {
                val newHeights = FloatArray(barCount)
                for (i in 0 until barCount) {
                    newHeights[i] = (heights[i] * 0.9f).coerceAtLeast(0.05f)
                }
                heights = newHeights
                barHeights.value = heights
                delay(50)
            }
        }
    }

    // Draw the visualization
    Canvas(modifier = modifier) {
        val heights = barHeights.value
        if (isPlaying) {
            drawSpectrum(heights)
        } else {
            drawIdleState(heights)
        }
    }
}

/**
 * Draw the audio spectrum visualization.
 */
private fun DrawScope.drawSpectrum(heights: FloatArray) {
    val width = size.width
    val height = size.height
    val barWidth = width / heights.size
    val primaryColor = Color(0xFF90CAF9) // Light blue

    heights.forEachIndexed { i, amplitude ->
        val barHeight = amplitude * height
        val startX = i * barWidth
        val startY = height
        val endY = height - barHeight

        drawLine(
            color = primaryColor,
            start = Offset(startX + barWidth / 2, startY),
            end = Offset(startX + barWidth / 2, endY),
            strokeWidth = barWidth * 0.8f,
            cap = StrokeCap.Round
        )
    }
}

/**
 * Draw an idle state when audio is not playing.
 */
private fun DrawScope.drawIdleState(heights: FloatArray) {
    val width = size.width
    val height = size.height
    val barWidth = width / heights.size
    val primaryColor = Color(0xFF90CAF9) // Light blue

    heights.forEachIndexed { i, amplitude ->
        val barHeight = amplitude * height
        val startX = i * barWidth
        val startY = height
        val endY = height - barHeight

        drawLine(
            color = primaryColor.copy(alpha = 0.5f),
            start = Offset(startX + barWidth / 2, startY),
            end = Offset(startX + barWidth / 2, endY),
            strokeWidth = barWidth * 0.8f,
            cap = StrokeCap.Round
        )
    }
}
