package gy.roach.radio.visualizers

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import gy.roach.radio.AudioPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.sin
import kotlin.random.Random

/**
 * Extension function to observe frequency data from an AudioPlayer.
 * Since we can't modify the AudioPlayer interface, this function simulates frequency data.
 */
@Composable
fun AudioPlayer.observeFrequencyData(): State<FloatArray> {
    // Create a mutable state to track the playing state
    val isPlayingState = remember { mutableStateOf(isPlaying()) }
    val frequencyData = remember { mutableStateOf(FloatArray(16) { 0.1f }) }
    val animationState = remember { mutableStateOf(0f) }

    // Use a key that will cause recomposition when the component is reused
    val compositionKey = remember { Any() }

    // Update the playing state periodically
    LaunchedEffect(compositionKey) {
        while (true) {
            try {
                isPlayingState.value = isPlaying()
                delay(100) // Check every 100ms
            } catch (e: Exception) {
                // Log error but don't crash
                println("Error checking isPlaying: ${e.message}")
                delay(500) // Longer delay if there was an error
            }
        }
    }

    // React to changes in the playing state
    // Using both compositionKey and isPlayingState.value ensures this effect is tied to the composition
    // and also reacts to playing state changes
    LaunchedEffect(compositionKey, isPlayingState.value) {
        try {
            if (isPlayingState.value) {
                // Initial animation state
                var currentAnimationState = animationState.value

                // Update frequency data a limited number of times
                var updateCount = 0
                val maxUpdates = 1000 // Safety limit to prevent infinite loops

                while (isPlayingState.value && updateCount < maxUpdates) {
                    // Update animation state
                    currentAnimationState += 0.05f
                    animationState.value = currentAnimationState

                    // Generate new frequency data based on animation state
                    val newData = FloatArray(16)
                    for (i in 0 until 16) {
                        // Create a wave-like pattern with some randomness
                        val baseHeight = sin((i * 0.2f + currentAnimationState) * 0.5f) * 0.3f + 0.5f
                        val randomFactor = Random.nextFloat() * 0.2f
                        newData[i] = (baseHeight + randomFactor).coerceIn(0.05f, 0.95f)
                    }
                    frequencyData.value = newData

                    // Delay for animation frame
                    delay(50)
                    updateCount++

                    // Check playing state again to ensure we're still playing
                    if (updateCount % 10 == 0) {
                        isPlayingState.value = isPlaying()
                    }
                }
            } else {
                // When not playing, gradually reduce frequency data to near zero
                var data = frequencyData.value
                var fadeCount = 0
                val maxFades = 20 // Limit the number of fade steps

                while (data.any { it > 0.1f } && fadeCount < maxFades) {
                    val newData = FloatArray(16)
                    for (i in 0 until 16) {
                        newData[i] = (data[i] * 0.9f).coerceAtLeast(0.05f)
                    }
                    data = newData
                    frequencyData.value = data
                    delay(50)
                    fadeCount++
                }

                // Ensure we have a minimum value
                if (fadeCount >= maxFades) {
                    frequencyData.value = FloatArray(16) { 0.1f }
                }
            }
        } catch (e: Exception) {
            // Log error but don't crash
            println("Error in frequency data animation: ${e.message}")
            // Set a default value in case of error
            frequencyData.value = FloatArray(16) { 0.1f }
        }
    }

    return frequencyData
}

/**
 * A visual equalizer component that displays frequency data as animated bars.
 *
 * @param audioPlayer The audio player to get frequency data from
 * @param modifier Modifier for the component
 * @param barColor The color of the equalizer bars
 * @param backgroundColor The background color of the equalizer
 * @param barCount The number of bars to display (default is 16)
 */
@Composable
fun EqualizerVisualizer(
    audioPlayer: AudioPlayer,
    modifier: Modifier = Modifier,
    barColor: Color = Color(0xFF00C853),
    backgroundColor: Color = Color(0x33000000),
    barCount: Int = 16
) {
    // Observe frequency data from the audio player
    val frequencyData by audioPlayer.observeFrequencyData()

    // Create animated values for each bar
    val animatedValues = List(barCount) { index ->
        val targetValue = if (index < frequencyData.size) frequencyData[index] else 0f
        val animatedValue by animateFloatAsState(
            targetValue = targetValue,
            animationSpec = tween(durationMillis = 100),
            label = "bar_$index"
        )
        animatedValue
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Draw background
        drawRoundRect(
            color = backgroundColor,
            cornerRadius = CornerRadius(8f, 8f),
            size = Size(canvasWidth, canvasHeight)
        )

        // Calculate bar width and spacing
        val barWidth = canvasWidth / (barCount * 2)
        val spacing = barWidth

        // Draw each bar
        for (i in 0 until barCount) {
            val value = animatedValues[i]
            val barHeight = canvasHeight * value

            // Calculate x position for the bar
            val x = i * (barWidth + spacing) + spacing

            // Draw the bar
            drawRoundRect(
                color = barColor,
                topLeft = Offset(x, canvasHeight - barHeight),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(4f, 4f)
            )
        }
    }
}

/**
 * A more colorful variant of the equalizer visualizer with gradient colors.
 *
 * @param audioPlayer The audio player to get frequency data from
 * @param modifier Modifier for the component
 * @param colors List of colors to use for the bars (cycles through them)
 * @param backgroundColor The background color of the equalizer
 * @param barCount The number of bars to display (default is 16)
 */
@Composable
fun ColorfulEqualizerVisualizer(
    audioPlayer: AudioPlayer,
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        Color(0xFF00C853), // Green
        Color(0xFF2196F3), // Blue
        Color(0xFFFFEB3B), // Yellow
        Color(0xFFE91E63)  // Pink
    ),
    backgroundColor: Color = Color(0x33000000),
    barCount: Int = 16
) {
    // Observe frequency data from the audio player
    val frequencyData by audioPlayer.observeFrequencyData()

    // Create animated values for each bar
    val animatedValues = List(barCount) { index ->
        val targetValue = if (index < frequencyData.size) frequencyData[index] else 0f
        val animatedValue by animateFloatAsState(
            targetValue = targetValue,
            animationSpec = tween(durationMillis = 100),
            label = "bar_$index"
        )
        animatedValue
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Draw background
        drawRoundRect(
            color = backgroundColor,
            cornerRadius = CornerRadius(8f, 8f),
            size = Size(canvasWidth, canvasHeight)
        )

        // Calculate bar width and spacing
        val barWidth = canvasWidth / (barCount * 2)
        val spacing = barWidth

        // Draw each bar
        for (i in 0 until barCount) {
            val value = animatedValues[i]
            val barHeight = canvasHeight * value

            // Calculate x position for the bar
            val x = i * (barWidth + spacing) + spacing

            // Select color based on index
            val color = colors[i % colors.size]

            // Draw the bar
            drawRoundRect(
                color = color,
                topLeft = Offset(x, canvasHeight - barHeight),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(4f, 4f)
            )
        }
    }
}
