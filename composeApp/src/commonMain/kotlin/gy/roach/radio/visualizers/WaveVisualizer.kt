package gy.roach.radio.visualizers

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun WaveVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val phase = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        )
    )

    Canvas(modifier = modifier.height(50.dp).fillMaxWidth()) {
        if (isPlaying) {
            val width = size.width
            val height = size.height
            val path = Path()

            // Start from bottom-left corner
            path.moveTo(0f, height)

            // Create wave points
            for (x in 0..width.toInt()) {
                val y = (sin(x * 0.03f + phase.value) * height * 0.2f + height / 2f).toFloat()
                path.lineTo(x.toFloat(), y)
            }

            // Complete the path to create a solid shape
            path.lineTo(width, height)  // Bottom-right corner
            path.lineTo(0f, height)     // Back to bottom-left corner
            path.close()

            drawPath(
                path = path,
                color = Color.Blue.copy(alpha = 0.5f)  // Semi-transparent blue
            )
        }
    }

}
