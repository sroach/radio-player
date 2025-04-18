package gy.roach.radio.visualizers

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

/**
 * Data class representing a floating balloon.
 */
data class Balloon(
    var x: Float,
    var y: Float,
    var radius: Float,
    var speedX: Float,
    var speedY: Float,
    val color: Color,
    var wobblePhase: Float = Random.nextFloat() * 2f * PI.toFloat()
)

/**
 * Function to generate a random color for a balloon.
 */
private fun randomBalloonColor(): Color {
    // Bright, festive balloon colors
    val colors = listOf(
        Color(0xFFFF3333), // Red
        Color(0xFF3366FF), // Blue
        Color(0xFFFFCC00), // Yellow
        Color(0xFF33CC33), // Green
        Color(0xFFFF66CC), // Pink
        Color(0xFFFF9900), // Orange
        Color(0xFF9933FF), // Purple
        Color(0xFF00CCCC)  // Teal
    )
    return colors[Random.nextInt(colors.size)]
}

/**
 * A visualizer that displays floating balloons with a lava lamp effect.
 *
 * @param isPlaying Whether audio is currently playing
 * @param modifier Modifier for the component
 * @param balloonCount The number of balloons to display
 */
@Composable
fun BalloonsVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    balloonCount: Int = 8
) {
    var canvasWidth by remember { mutableStateOf(0f) }
    var canvasHeight by remember { mutableStateOf(0f) }

    // Create balloons with random properties
    var balloons by remember { mutableStateOf(List(balloonCount) {
        Balloon(
            x = Random.nextFloat() * 300f, // Will be adjusted when canvas size is known
            y = Random.nextFloat() * 300f,
            radius = 25f + Random.nextFloat() * 30f, // Random radius between 25 and 55
            speedX = Random.nextFloat() * 1.5f - 0.75f, // Random speed between -0.75 and 0.75
            speedY = -0.5f - Random.nextFloat() * 1.5f, // Upward movement (negative Y)
            color = randomBalloonColor(),
            wobblePhase = Random.nextFloat() * 2f * PI.toFloat()
        )
    }) }

    // Animation for wobble effect
    val infiniteTransition = rememberInfiniteTransition()
    val wobble = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing)
        )
    )

    // Animation effect to update balloon positions
    LaunchedEffect(canvasWidth, canvasHeight) {
        while (true) {
            if (isPlaying) {
                balloons = balloons.map { balloon ->
                    // Update balloon position with wobble effect
                    balloon.x += balloon.speedX + sin(balloon.wobblePhase + wobble.value) * 0.4f

                    // Add a slight bobbing motion to make balloons float more naturally
                    val bobbingEffect = sin(wobble.value * 0.5f + balloon.wobblePhase) * 0.2f
                    balloon.y += balloon.speedY + bobbingEffect

                    // Handle horizontal boundaries with gentle bounce
                    if (balloon.x - balloon.radius < 0) {
                        balloon.x = balloon.radius
                        balloon.speedX = abs(balloon.speedX) * 0.8f // Reduce speed after bounce
                    } else if (balloon.x + balloon.radius > canvasWidth && canvasWidth > 0) {
                        balloon.x = canvasWidth - balloon.radius
                        balloon.speedX = -abs(balloon.speedX) * 0.8f // Reduce speed after bounce
                    }

                    // Wrap around when balloon reaches top or bottom
                    if (balloon.y + balloon.radius < 0) {
                        // Wrap from top to bottom
                        balloon.y = canvasHeight + balloon.radius
                        balloon.x = Random.nextFloat() * canvasWidth
                        balloon.speedY = -0.5f - Random.nextFloat() * 1.5f // Reset speed
                        balloon.radius = 25f + Random.nextFloat() * 30f // Randomize size again
                    } else if (balloon.y - balloon.radius > canvasHeight && canvasHeight > 0) {
                        // Wrap from bottom to top (shouldn't happen often with upward movement)
                        balloon.y = -balloon.radius
                        balloon.x = Random.nextFloat() * canvasWidth
                    }

                    balloon
                }
            }
            delay(16) // Approximately 60 FPS
        }
    }

    // Draw the balloons on the canvas
    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(300.dp)
    ) {
        // Update canvas dimensions
        canvasWidth = size.width
        canvasHeight = size.height

        // Draw background
        drawRect(
            color = Color(0xFF001133), // Dark blue background
            size = size
        )

        // Draw balloons
        balloons.forEach { balloon ->
            drawBalloon(
                centerX = balloon.x,
                centerY = balloon.y,
                radius = balloon.radius,
                baseColor = balloon.color,
                wobble = wobble.value + balloon.wobblePhase
            )
        }
    }
}

/**
 * Draws a balloon with a teardrop shape, knot, and string.
 */
private fun DrawScope.drawBalloon(
    centerX: Float,
    centerY: Float,
    radius: Float,
    baseColor: Color,
    wobble: Float
) {
    // Create balloon body path (teardrop shape)
    val balloonPath = Path()
    val points = 24
    val radiusModifier = 0.15f // Controls how much the shape wobbles

    // Create the top part of the balloon (more rounded)
    for (i in 0 until points / 2) {
        val angle = (i.toFloat() / points) * 2f * PI.toFloat()
        val wobbleOffset = sin(angle * 3f + wobble) * radiusModifier
        // Make the balloon slightly wider than tall
        val xRadius = radius * 1.1f
        val yRadius = radius * 0.95f
        val currentXRadius = xRadius * (1f + wobbleOffset)
        val currentYRadius = yRadius * (1f + wobbleOffset)

        val x = centerX + cos(angle) * currentXRadius
        val y = centerY + sin(angle) * currentYRadius

        if (i == 0) {
            balloonPath.moveTo(x, y)
        } else {
            balloonPath.lineTo(x, y)
        }
    }

    // Create the bottom part of the balloon (more tapered)
    for (i in points / 2 until points) {
        val angle = (i.toFloat() / points) * 2f * PI.toFloat()
        val wobbleOffset = sin(angle * 3f + wobble) * radiusModifier
        // Make the bottom part more tapered
        val progress = (i - points / 2).toFloat() / (points / 2)
        val taperFactor = 1f - progress * 0.3f

        val xRadius = radius * 1.1f * taperFactor
        val yRadius = radius * (0.95f + progress * 0.15f) // Slightly elongate

        val currentXRadius = xRadius * (1f + wobbleOffset)
        val currentYRadius = yRadius * (1f + wobbleOffset)

        val x = centerX + cos(angle) * currentXRadius
        val y = centerY + sin(angle) * currentYRadius

        balloonPath.lineTo(x, y)
    }
    balloonPath.close()

    // Create a lighter version of the base color for the gradient
    val lighterColor = Color(
        red = min(baseColor.red + 0.3f, 1f),
        green = min(baseColor.green + 0.3f, 1f),
        blue = min(baseColor.blue + 0.3f, 1f),
        alpha = 0.9f
    )

    // Create radial gradient for balloon
    val gradientBrush = Brush.radialGradient(
        colors = listOf(lighterColor, baseColor),
        center = Offset(centerX - radius * 0.3f, centerY - radius * 0.3f), // Offset for 3D effect
        radius = radius * 1.2f
    )

    // Draw the balloon body
    drawPath(
        path = balloonPath,
        brush = gradientBrush,
        alpha = 0.9f
    )

    // Draw the knot at the bottom
    val knotY = centerY + radius * 1.05f
    val knotRadius = radius * 0.15f

    drawCircle(
        color = baseColor,
        radius = knotRadius,
        center = Offset(centerX, knotY)
    )

    // Draw the string
    val stringPath = Path()
    val stringLength = radius * 2f
    val waviness = radius * 0.2f
    val segments = 8

    stringPath.moveTo(centerX, knotY + knotRadius)

    for (i in 1..segments) {
        val progress = i.toFloat() / segments
        val waveOffset = sin(progress * PI.toFloat() * 2 + wobble) * waviness

        stringPath.lineTo(
            centerX + waveOffset,
            knotY + knotRadius + stringLength * progress
        )
    }

    drawPath(
        path = stringPath,
        color = baseColor.copy(alpha = 0.7f),
        style = Stroke(width = radius * 0.05f)
    )
}
