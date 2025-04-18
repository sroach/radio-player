package gy.roach.radio.visualizers

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CircleVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale = infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = modifier.size(50.dp)) {
        if (isPlaying) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = (size.minDimension / 2f) * scale.value

            drawCircle(
                color = Color.Red.copy(alpha = 0.5f),
                center = center,
                radius = radius
            )
        }
    }
}
