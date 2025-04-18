package gy.roach.radio.visualizers

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.random.Random


@Composable
fun BarVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    val barCount = 20
    val infiniteTransition = rememberInfiniteTransition()

    // Generate random colors for each bar
    val randomColor1 = Color(
        red = Random.nextFloat(),
        green = Random.nextFloat(),
        blue = Random.nextFloat(),
        alpha = 1f
    )
    val randomColor2 = randomColor1.copy(alpha = 0.5f) // Darker version for gradient

    val barHeights = List(barCount) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 500 + Random.nextInt(1000),
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Canvas(modifier = modifier.height(50.dp).fillMaxWidth()) {
        // Draw the caret rectangle (border) around the visualization area
        drawRect(
            color = Color.Gray,
            topLeft = Offset(0f, 0f),
            size = Size(size.width, size.height),
            style = Stroke(width = 2f)
        )

        if (isPlaying) {
            val barWidth = size.width / (barCount * 2f)
            barHeights.forEachIndexed { index, heightState ->
                val height = size.height * heightState.value
                // Create a top-to-bottom gradient for each bar with random colors
                val gradient = Brush.verticalGradient(
                    colors = listOf(randomColor1, randomColor2),
                    startY = size.height - height, // Top of the bar
                    endY = size.height // Bottom of the bar
                )
                drawRect(
                    brush = gradient,
                    topLeft = Offset(index * barWidth * 2f, size.height - height),
                    size = Size(barWidth, height)
                )
            }
        }
    }
}
