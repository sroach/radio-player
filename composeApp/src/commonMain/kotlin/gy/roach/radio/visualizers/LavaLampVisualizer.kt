package gy.roach.radio.visualizers

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

@Composable
fun LavaLampVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    blobCount: Int = 4
) {
    class Blob(
        var x: Float,
        var y: Float,
        var radius: Float,
        var speedX: Float,
        var speedY: Float,
        var wobblePhase: Float = Random.nextFloat() * 2f * PI.toFloat()
    )

    var canvasWidth by remember { mutableStateOf(0f) }
    var canvasHeight by remember { mutableStateOf(0f) }

    var blobs by remember { mutableStateOf(List(blobCount) {
        Blob(
            x = Random.nextFloat() * 300f, // Will be adjusted when canvas size is known
            y = Random.nextFloat() * 300f,
            radius = 30f + Random.nextFloat() * 20f,
            speedX = Random.nextFloat() * 2f - 1f, // Random speed between -1 and 1
            speedY = -0.5f - Random.nextFloat(),
            wobblePhase = Random.nextFloat() * 2f * PI.toFloat()
        )
    }) }

    val infiniteTransition = rememberInfiniteTransition()
    val wobble = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing)
        )
    )

    LaunchedEffect(isPlaying, canvasWidth, canvasHeight) {
        while (isPlaying) {
            blobs = blobs.map { blob ->
                // Update blob position
                blob.y += blob.speedY
                blob.x += blob.speedX + sin(blob.wobblePhase + wobble.value) * 0.5f

                // Handle horizontal boundaries
                if (blob.x - blob.radius < 0) {
                    // Bounce off left edge
                    blob.x = blob.radius
                    blob.speedX = abs(blob.speedX)
                } else if (blob.x + blob.radius > canvasWidth && canvasWidth > 0) {
                    // Bounce off right edge
                    blob.x = canvasWidth - blob.radius
                    blob.speedX = -abs(blob.speedX)
                }

                // Wrap around when blob reaches top or bottom
                if (blob.y + blob.radius < 0) {
                    // Wrap from top to bottom
                    blob.y = canvasHeight + blob.radius
                    blob.x = Random.nextFloat() * canvasWidth
                } else if (blob.y - blob.radius > canvasHeight && canvasHeight > 0) {
                    // Wrap from bottom to top
                    blob.y = -blob.radius
                    blob.x = Random.nextFloat() * canvasWidth
                }

                blob
            }
            delay(16) // Approximately 60 FPS
        }
    }

    val gradient = remember {
        object {
            val colors = listOf(
                Color(0xFFFF3366),
                Color(0xFFFF6633),
                Color(0xFFFFCC33)
            )
        }
    }

    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(300.dp)
    ) {
        // Update canvas dimensions
        canvasWidth = size.width
        canvasHeight = size.height

        if (isPlaying) {
            // Draw background
            drawRect(
                color = Color(0xFF330033),
                size = size
            )

            // Draw blobs
            blobs.forEach { blob ->
                drawBlob(
                    centerX = blob.x,
                    centerY = blob.y,
                    radius = blob.radius,
                    wobble = wobble.value + blob.wobblePhase,
                    gradient = gradient.colors
                )
            }
        }
    }
}

private fun DrawScope.drawBlob(
    centerX: Float,
    centerY: Float,
    radius: Float,
    wobble: Float,
    gradient: List<Color>
) {
    val path = Path()
    val points = 12
    val radiusModifier = 0.2f

    for (i in 0..points) {
        val angle = (i.toFloat() / points) * 2f * PI.toFloat()
        val wobbleOffset = sin(angle * 3f + wobble) * radiusModifier
        val currentRadius = radius * (1f + wobbleOffset)
        val x = centerX + cos(angle) * currentRadius
        val y = centerY + sin(angle) * currentRadius

        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()

    // Create radial gradient for blob
    val gradientBrush = Brush.radialGradient(
        colors = gradient,
        center = Offset(centerX, centerY),
        radius = radius * 1.2f
    )

    drawPath(
        path = path,
        brush = gradientBrush,
        alpha = 0.8f
    )
}
