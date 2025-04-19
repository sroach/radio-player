package gy.roach.radio.visualizers


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun SpectrumVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    // Number of bars in our equalizer visualizer
    val barCount = 10
    val barWidthDp = 4.dp
    val barGapDp = 2.dp

    // Convert dp to pixels inside the Canvas
    val barWidth = with(LocalDensity.current) { barWidthDp.toPx() }
    val barGap = with(LocalDensity.current) { barGapDp.toPx() }

    // Create list of animated values representing each bar height factor (0 – 1 range)
    val barHeights = remember { List(barCount) { Animatable(initialValue = 0.0f) } }

    // Trigger animations
    LaunchedEffect(isPlaying) {
        // When playing, each bar will continuously animate to new random heights.
        // When stopped, we animate them down to 0.
        if (isPlaying) {
            // launch a coroutine for each bar to simulate independent animation
            barHeights.forEach { animatable ->
                launch {
                    // Loop to simulate continuous updates
                    while (isActive && isPlaying) {
                        val target = Random.nextFloat() * 0.8f + 0.2f // random value between 0.2 and 1.0
                        animatable.animateTo(
                            target,
                            animationSpec = tween(durationMillis = Random.nextInt(300, 600), easing = LinearEasing)
                        )
                        // Small delay before the next animation change
                        delay(Random.nextLong(50, 150))
                    }
                }
            }
        } else {
            // When not playing, animate all bars to a low value (0)
            barHeights.forEach { animatable ->
                launch {
                    animatable.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 400)
                    )
                }
            }
        }
    }
    val color = MaterialTheme.colorScheme.primary

    // Draw the equalizer bars inside a Canvas
    Canvas(modifier = modifier) {
        // The total width needed for each bar including the gap
        val totalBarWidth = barWidth + barGap

        // Base line (bottom)
        val canvasHeight = size.height

        barHeights.forEachIndexed { index, animatable ->
            // The height of this bar relative to the canvas height.
            val barHeight = animatable.value * canvasHeight

            // X position calculation for each bar.
            val x = index * totalBarWidth

            // Draw a rectangle for each bar.
            drawRect(
                color = color,
                topLeft = androidx.compose.ui.geometry.Offset(x, canvasHeight - barHeight),
                size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
            )
        }
    }
}

