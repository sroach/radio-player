package gy.roach.radio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlinx.coroutines.delay

/**
 * iOS implementation of AudioSpectrum.
 * Displays a real-time audio spectrum visualization using AudioAnalyzer.
 */
@Composable
actual fun AudioSpectrum(
    isPlaying: Boolean,
    modifier: Modifier
) {
    // Number of bars in the equalizer
    val barCount = 16

    // State for bar heights
    val barHeights = remember { mutableStateOf(FloatArray(barCount) { 0.1f }) }

    // Start/stop the audio analyzer based on playback state
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            // Start the audio analyzer
            AudioAnalyzerManager.start()

            // Continuously update the visualization while playing
            while (isPlaying) {
                // Get real frequency data from the audio analyzer
                val frequencyData = AudioAnalyzerManager.getFrequencyData()

                // Update bar heights with real data
                barHeights.value = frequencyData

                // Delay for animation frame
                delay(50)
            }

            // Stop the audio analyzer when not playing
            AudioAnalyzerManager.stop()
        } else {
            // Stop the audio analyzer
            AudioAnalyzerManager.stop()

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
    val primaryColor = Color(0xFF007AFF) // iOS blue

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
    val primaryColor = Color(0xFF007AFF) // iOS blue

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
